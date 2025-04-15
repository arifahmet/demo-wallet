package com.arif.demo.model.web.transaction;

import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeTransactionStatusRequestDto {
    @NotBlank
    private String transactionKey;
    @NotNull
    private TransactionStatusEnum transactionStatus;
}
