package com.arif.demo.model.web.transaction;

import com.arif.demo.model.enums.TransactionStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeTransactionStatusRequestDto {
    @NotBlank
    private String transactionKey;
    @NotNull
    private TransactionStatusEnum transactionStatus;
}
