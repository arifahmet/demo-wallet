package com.arif.demo.model.web.transaction;

import com.arif.demo.config.MoneySerializer;
import com.arif.demo.model.entity.TransactionEntity;
import com.arif.demo.model.enums.TransactionStatusEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponseDto {
    private String transactionKey;
    private TransactionStatusEnum transactionStatus;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount;

    public static TransactionResponseDto of(TransactionEntity transactionEntity) {
        return TransactionResponseDto.builder()
                .transactionKey(transactionEntity.getTransactionKey())
                .transactionStatus(transactionEntity.getTransactionStatus())
                .amount(transactionEntity.getAmount()).build();
    }
}
