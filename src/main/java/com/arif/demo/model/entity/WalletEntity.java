package com.arif.demo.model.entity;

import com.arif.demo.model.web.wallet.CreateWalletRequestDto;
import com.arif.demo.model.web.wallet.CurrenyEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@ToString
@Table("dm_wallet")
public class WalletEntity extends BaseEntity {
    @Id
    @Column("ID")
    private Long id;
    @Column("USER_ID")
    private Long userId;
    @Column("WALLET_NAME")
    private String walletName;
    @Column("CURRENCY")
    private CurrenyEnum currency;
    @Column("USABLE_BALANCE")
    private BigDecimal usableBalance;
    @Column("BLOCKED_BALANCE")
    private BigDecimal blockedBalance;
    @Column("ACTIVE_FOR_WITHDRAW")
    private boolean activeForWithdraw;
    @Column("ACTIVE_FOR_MARKET")
    private boolean activeForShopping;


    public static WalletEntity of(UserEntity user, CreateWalletRequestDto createWalletRequest) {
        var created = LocalDateTime.now();
        return WalletEntity.builder()
                .userId(user.getId())
                .walletName(createWalletRequest.getWalletName())
                .currency(createWalletRequest.getCurrency())
                .usableBalance(BigDecimal.ZERO)
                .blockedBalance(BigDecimal.ZERO)
                .activeForWithdraw(createWalletRequest.isActiveForWithdraw())
                .activeForShopping(createWalletRequest.isActiveForShopping())
                .created(created)
                .updated(created)
                .build();
    }
}
