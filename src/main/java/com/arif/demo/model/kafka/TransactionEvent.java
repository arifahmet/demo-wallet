package com.arif.demo.model.kafka;

import com.arif.demo.model.entity.TransactionEntity;
import com.arif.demo.model.entity.WalletEntity;
import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionEvent {
    private String transactionKey;
    private String walletName;
    private TransactionTypeEnum transactionType;
    private TransactionStatusEnum transactionStatus;

    public static TransactionEvent of(TransactionEntity transaction, WalletEntity wallet, TransactionStatusEnum transactionStatus) {
        return TransactionEvent.builder()
                .transactionKey(transaction.getTransactionKey())
                .walletName(wallet.getWalletName())
                .transactionType(transaction.getTransactionType())
                .transactionStatus(transactionStatus).build();
    }
}
