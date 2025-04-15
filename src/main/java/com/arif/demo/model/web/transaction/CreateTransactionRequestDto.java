package com.arif.demo.model.web.transaction;

import com.arif.demo.model.enums.OppositePartyTypeEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionRequestDto {
    @NotBlank
    @Size(max = 250)
    private String walletName;
    @NotNull
    private TransactionTypeEnum transactionType;
    @NotNull
    private OppositePartyTypeEnum oppositePartyType;
    @DecimalMin(value = "0.01")
    @Digits(integer = 10, fraction = 2)
    @NotNull
    private BigDecimal amount;
}
