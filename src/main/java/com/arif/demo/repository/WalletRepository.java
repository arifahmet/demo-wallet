package com.arif.demo.repository;

import com.arif.demo.model.entity.WalletEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface WalletRepository extends ReactiveCrudRepository<WalletEntity, Long> {
    @Query("""
            SELECT w.*
            FROM dm_user u,
                 dm_wallet w
            WHERE u.USER_KEY = :userKey
              AND w.USER_ID = u.ID
            """)
    Flux<WalletEntity> findUserWallets(String userKey);

    @Query("""
            SELECT w.*
            FROM dm_user u,
                 dm_wallet w
            WHERE u.USER_KEY = :userKey
              AND w.USER_ID = u.ID
              AND w.WALLET_NAME = :walletName
            """)
    Mono<WalletEntity> findWallet(String userKey, String walletName);

    @Modifying
    @Query("""
            UPDATE dm_wallet
            SET USABLE_BALANCE= USABLE_BALANCE + :usableMountChange, BLOCKED_BALANCE = BLOCKED_BALANCE + :blockAmountChange
            WHERE ID = :walletId
            """)
    Mono<Void> changeBalance(Long walletId, BigDecimal usableMountChange, BigDecimal blockAmountChange);

    @Modifying
    @Query("""
            UPDATE dm_wallet
            SET ACTIVE_FOR_WITHDRAW= :withdrawStatus
            WHERE ID = :walletId
            """)
    Mono<Void> updateWithdrawStatus(Long walletId, boolean withdrawStatus);
}
