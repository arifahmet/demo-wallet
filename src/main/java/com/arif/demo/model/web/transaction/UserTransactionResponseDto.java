package com.arif.demo.model.web.transaction;

import com.arif.demo.config.MoneySerializer;
import com.arif.demo.model.enums.OppositePartyTypeEnum;
import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserTransactionResponseDto {
    private String walletName;
    private String transactionKey;
    private TransactionTypeEnum transactionType;
    private TransactionStatusEnum transactionStatus;
    private OppositePartyTypeEnum oppositePartyType;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime statusChangeTime;

}
