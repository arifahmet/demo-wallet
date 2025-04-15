package com.arif.demo.repository;

import com.arif.demo.model.entity.TransactionEntity;
import com.arif.demo.model.enums.TransactionStatusEnum;
import com.arif.demo.model.enums.TransactionTypeEnum;
import com.arif.demo.model.web.transaction.UserTransactionResponseDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveCrudRepository<TransactionEntity, Long> {
    @Query("""
            SELECT w.WALLET_NAME,
                   t.TRANSACTION_KEY,
                   t.TRANSACTION_TYPE,
                   t.TRANSACTION_STATUS,
                   t.AMOUNT,
                   t.DESCRIPTION,
                   t.STATUS_CHANGE_TIME
            FROM dm_user u,
                 dm_wallet w,
                 dm_transaction t
            WHERE u.USER_KEY = :userKey
              AND w.USER_ID = u.ID
              AND t.WALLET_ID = w.ID
              AND (w.WALLET_NAME = :walletName OR :walletName IS NULL)
              AND (t.TRANSACTION_STATUS = :status OR :status IS NULL)
              AND (t.TRANSACTION_TYPE = :transactionType OR :transactionType IS NULL)
            """)
    Flux<UserTransactionResponseDto> findUserTransactions(String userKey, String walletName, TransactionStatusEnum status, TransactionTypeEnum transactionType);

    Mono<TransactionEntity> findByTransactionKey(String transactionKey);
}
