package com.arif.demo.service.impl;

import com.arif.demo.model.entity.TransactionEntity;
import com.arif.demo.model.entity.WalletEntity;
import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import com.arif.demo.model.kafka.TransactionEvent;
import com.arif.demo.model.kafka.enums.Topics;
import com.arif.demo.model.web.transaction.ChangeTransactionStatusRequestDto;
import com.arif.demo.model.web.transaction.CreateTransactionRequestDto;
import com.arif.demo.model.web.transaction.TransactionResponseDto;
import com.arif.demo.model.web.transaction.UserTransactionResponseDto;
import com.arif.demo.repository.TransactionRepository;
import com.arif.demo.security.TokenUtil;
import com.arif.demo.service.TransactionService;
import com.arif.demo.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.kafka.sender.SenderResult;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    @Value("${transaction-limit:1000}")
    public int transactionLimit;
    private final TransactionRepository transactionRepository;
    private final WalletService walletService;
    private final TokenUtil tokenUtil;
    private final ReactiveKafkaProducerTemplate<String, TransactionEvent> transactionProducerTemplate;

    @Override
    public Flux<UserTransactionResponseDto> getUserTransactions(String walletName, TransactionStatusEnum status, TransactionTypeEnum transactionType) {
        return tokenUtil.getUserKey().flatMapMany(userKey ->
                transactionRepository.findUserTransactions(userKey, walletName, status, transactionType));
    }

    @Override
    public Mono<TransactionResponseDto> createTransaction(CreateTransactionRequestDto transactionRequest) {
        return walletService.getUserWalletByName(transactionRequest.getWalletName())
                .flatMap(wallet -> createAndProcessTransaction(wallet, transactionRequest))
                .map(TransactionResponseDto::of);
    }

    @Override
    public Mono<Void> changeTransactionStatus(ChangeTransactionStatusRequestDto changeTransactionStatusRequest) {
        return validateChangeTransactionStatusRequest(changeTransactionStatusRequest)
                .then(transactionRepository.findByTransactionKey(changeTransactionStatusRequest.getTransactionKey()).flatMap(transaction ->
                        walletService.getUserWalletById(transaction.getWalletId()).flatMap(wallet ->
                {
                    if (isTransactionProceedBefore(transaction.getTransactionStatus())) {
                        return Mono.error(new NotImplementedException("Transaction already processed"));
                    }
                    var event = TransactionEvent.of(transaction, wallet, changeTransactionStatusRequest.getTransactionStatus());
                    return transactionProducerTemplate.send(Topics.TRANSACTION_EVENT.name(), wallet.getWalletName(), event)
                            .flatMap(this::checkSenderResultException);
                }))).then();
    }

    private Mono<Void> validateChangeTransactionStatusRequest(ChangeTransactionStatusRequestDto transactionRequest) {
        if (!(TransactionStatusEnum.APPROVED.equals(transactionRequest.getTransactionStatus())
                || TransactionStatusEnum.DENIED.equals(transactionRequest.getTransactionStatus()))) {
            return Mono.error(new NotImplementedException("Invalid transaction status"));
        }
        return Mono.empty();
    }

    @Override
    public Mono<Void> consumeTransactionEvent(ReceiverRecord<String, TransactionEvent> receiverRecord) {
        return transactionRepository.findByTransactionKey(receiverRecord.value().getTransactionKey()).flatMap(transaction ->
        {
            if (isTransactionProceedBefore(transaction.getTransactionStatus())) {
                return Mono.error(new NotImplementedException("Transaction already processed"));
            }
            transaction.setTransactionStatus(receiverRecord.value().getTransactionStatus());
            return processTransaction(transaction);
        }).then();
    }

    private Mono<TransactionEntity> saveTransaction(TransactionEntity transaction) {
        return transactionRepository.save(transaction);
    }

    private Mono<TransactionEntity> createAndProcessTransaction(WalletEntity walletEntity,
                                                                CreateTransactionRequestDto transactionRequest) {
        var transactionStatus = getTransactionStatus(transactionRequest);
        var newTransaction = TransactionEntity.of(transactionStatus, walletEntity, transactionRequest, null);
        return processTransaction(newTransaction);
    }

    private Mono<TransactionEntity> processTransaction(TransactionEntity transaction) {
        return walletService.changeUserWalletBalance(transaction)
                .onErrorResume(error -> handleTransactionError(transaction, error).then())
                .then(saveTransaction(transaction));
    }

    private Mono<TransactionEntity> handleTransactionError(TransactionEntity transaction, Throwable error) {
        transaction.setTransactionStatus(TransactionStatusEnum.FAILED);
        transaction.setDescription(error.getMessage());
        return Mono.empty();
    }

    private TransactionStatusEnum getTransactionStatus(CreateTransactionRequestDto transactionRequest) {
        return transactionRequest.getAmount().compareTo(BigDecimal.valueOf(transactionLimit)) < 0 ?
                TransactionStatusEnum.APPROVED : TransactionStatusEnum.PENDING;
    }

    private boolean isTransactionProceedBefore(TransactionStatusEnum transactionStatus) {
        return !TransactionStatusEnum.PENDING.equals(transactionStatus);
    }

    private Mono<SenderResult<Void>> checkSenderResultException(SenderResult<Void> senderResult) {
        if (senderResult.exception() != null) {
            return Mono.error(senderResult.exception());
        }
        return Mono.just(senderResult);
    }
}
