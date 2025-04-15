package com.arif.demo.model.web.wallet;

import com.arif.demo.config.MoneySerializer;
import com.arif.demo.model.entity.WalletEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class GetUserWalletResponseDto {
    private String walletName;
    private CurrenyEnum currency;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal usableBalance;
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal blockedBalance;

    public static GetUserWalletResponseDto of(WalletEntity walletEntity) {
        return GetUserWalletResponseDto.builder()
                .walletName(walletEntity.getWalletName())
                .currency(walletEntity.getCurrency())
                .usableBalance(walletEntity.getUsableBalance())
                .blockedBalance(walletEntity.getBlockedBalance())
                .build();
    }
}
