package com.arif.demo.model.entity;

import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import com.arif.demo.model.web.transaction.CreateTransactionRequestDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@ToString
@Table("dm_transaction")
public class TransactionEntity extends BaseEntity {
    @Id
    @Column("ID")
    private Long id;
    @Column("WALLET_ID")
    private Long walletId;
    @Column("TRANSACTION_KEY")
    private String transactionKey;
    @Column("TRANSACTION_TYPE")
    private TransactionTypeEnum transactionType;
    @Column("TRANSACTION_STATUS")
    private TransactionStatusEnum transactionStatus;
    @Column("AMOUNT")
    private BigDecimal amount;
    @Column("DESCRIPTION")
    private String description;
    @CreatedDate
    @Column("STATUS_CHANGE_TIME")
    private LocalDateTime statusChangedAt;


    public static TransactionEntity of(
                                       WalletEntity walletEntity,
                                       CreateTransactionRequestDto transactionRequest,
                                       String description) {
        var created = LocalDateTime.now();
        return TransactionEntity.builder()
                .walletId(walletEntity.getId())
                .transactionKey(UUID.randomUUID().toString())
                .transactionType(transactionRequest.getTransactionType())
                .transactionStatus(TransactionStatusEnum.PENDING)
                .amount(transactionRequest.getAmount())
                .statusChangedAt(null)
                .description(description)
                .created(created)
                .updated(created)
                .build();
    }
}
