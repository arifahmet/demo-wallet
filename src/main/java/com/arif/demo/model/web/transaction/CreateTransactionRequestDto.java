package com.arif.demo.model.web.transaction;

import com.arif.demo.model.enums.TransactionTypeEnum;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionRequestDto {
    @NotBlank
    private String walletName;
    @NotNull
    private TransactionTypeEnum transactionType;
    @DecimalMin(value = "0.01")
    @Digits(integer = 10, fraction = 2)
    @NotNull
    private BigDecimal amount;
}
