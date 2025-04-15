package com.arif.demo.controller.Impl;

import com.arif.demo.controller.TransactionOperation;
import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import com.arif.demo.model.web.transaction.ChangeTransactionStatusRequestDto;
import com.arif.demo.model.web.transaction.CreateTransactionRequestDto;
import com.arif.demo.model.web.transaction.TransactionResponseDto;
import com.arif.demo.model.web.transaction.UserTransactionResponseDto;
import com.arif.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionOperation {

    private final TransactionService transactionService;

    @Override
    public Flux<UserTransactionResponseDto> getUserTransactions(String walletName, TransactionStatusEnum status, TransactionTypeEnum transactionType) {
        return transactionService.getUserTransactions(walletName, status, transactionType);
    }

    @Override
    public Mono<TransactionResponseDto> createTransaction(CreateTransactionRequestDto transactionRequest) {
        return transactionService.createTransaction(transactionRequest);
    }

    @Override
    public Mono<Void> changeTransactionStatus(ChangeTransactionStatusRequestDto changeTransactionStatusRequest) {
        return transactionService.changeTransactionStatus(changeTransactionStatusRequest);
    }
}

