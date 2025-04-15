package com.arif.demo.service.impl;

import com.arif.demo.exception.model.WalletNotFoundException;
import com.arif.demo.model.entity.TransactionEntity;
import com.arif.demo.model.entity.WalletEntity;
import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import com.arif.demo.model.kafka.TransactionEvent;
import com.arif.demo.model.web.transaction.CreateTransactionRequestDto;
import com.arif.demo.model.web.transaction.TransactionResponseDto;
import com.arif.demo.repository.TransactionRepository;
import com.arif.demo.security.TokenUtil;
import com.arif.demo.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TransactionServiceImplTest {
    @InjectMocks
    TransactionServiceImpl service;
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WalletService walletService;

    @Mock
    private TokenUtil tokenUtil;

    @Mock
    private ReactiveKafkaProducerTemplate<String, TransactionEvent> transactionProducerTemplate;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "transactionLimit", new BigDecimal("5000"));
    }

    @Test
    void createTransaction_shouldReturnTransactionResponseDto_whenValidRequest() {
        // Arrange
        CreateTransactionRequestDto requestDto = new CreateTransactionRequestDto();
        requestDto.setWalletName("testWallet");
        requestDto.setTransactionType(TransactionTypeEnum.DEPOSIT);
        requestDto.setAmount(BigDecimal.valueOf(500));

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(1L);
        walletEntity.setWalletName("testWallet");
        walletEntity.setUsableBalance(BigDecimal.valueOf(1000));

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(requestDto.getAmount());
        transactionEntity.setTransactionType(requestDto.getTransactionType());
        transactionEntity.setTransactionStatus(TransactionStatusEnum.PENDING);
        transactionEntity.setWalletId(1L);

        when(walletService.getUserWalletByName(anyString())).thenReturn(Mono.just(walletEntity));
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(Mono.just(transactionEntity));
        when(walletService.changeUserWalletBalance(any(TransactionEntity.class))).thenReturn(Mono.just(walletEntity));
        when(transactionProducerTemplate.send(any(String.class), any(String.class), any()))
                .thenReturn(Mono.empty());


        // Act
        Mono<TransactionResponseDto> result = service.createTransaction(requestDto);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(transactionResponseDto ->
                        transactionResponseDto.getAmount().equals(requestDto.getAmount()))
                .verifyComplete();
    }

    @Test
    void createTransaction_shouldThrowError_whenWalletNotFound() {
        // Arrange
        CreateTransactionRequestDto requestDto = new CreateTransactionRequestDto();
        requestDto.setWalletName("nonExistentWallet");
        requestDto.setTransactionType(TransactionTypeEnum.DEPOSIT);
        requestDto.setAmount(BigDecimal.valueOf(200));

        when(walletService.getUserWalletByName("nonExistentWallet")).thenReturn(Mono.error(new WalletNotFoundException()));

        // Act
        Mono<TransactionResponseDto> result = service.createTransaction(requestDto);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof WalletNotFoundException)
                .verify();
    }
}