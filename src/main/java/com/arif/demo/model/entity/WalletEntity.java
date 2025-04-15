package com.arif.demo.model.entity;

import com.arif.demo.model.web.wallet.CreateWalletRequestDto;
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
    private String currency;
    @Column("USABLE_BALANCE")
    private BigDecimal usableBalance;
    @Column("BLOCKED_BALANCE")
    private BigDecimal blockedBalance;


    public static WalletEntity of(UserEntity user, CreateWalletRequestDto createWalletRequest) {
        var created = LocalDateTime.now();
        return WalletEntity.builder()
                .userId(user.getId())
                .walletName(createWalletRequest.getWalletName())
                .currency(createWalletRequest.getCurrency().name())
                .usableBalance(BigDecimal.ZERO)
                .blockedBalance(BigDecimal.ZERO)
                .created(created)
                .updated(created)
                .build();
    }
}
