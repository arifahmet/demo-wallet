package com.arif.demo.service;

import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import com.arif.demo.model.kafka.TransactionEvent;
import com.arif.demo.model.web.transaction.ChangeTransactionStatusRequestDto;
import com.arif.demo.model.web.transaction.CreateTransactionRequestDto;
import com.arif.demo.model.web.transaction.TransactionResponseDto;
import com.arif.demo.model.web.transaction.UserTransactionResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

public interface TransactionService {

    Flux<UserTransactionResponseDto> getUserTransactions(String walletName, TransactionStatusEnum status, TransactionTypeEnum transactionType);

    Mono<TransactionResponseDto> createTransaction(CreateTransactionRequestDto transactionRequest);

    Mono<Void> changeTransactionStatus(ChangeTransactionStatusRequestDto transactionRequest);

    Mono<Void> consumeTransactionEvent(ReceiverRecord<String, TransactionEvent> receiverRecord);
}
