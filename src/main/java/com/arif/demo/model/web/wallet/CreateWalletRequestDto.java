package com.arif.demo.model.web.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateWalletRequestDto {
    @NotBlank
    private String walletName;
    @NotNull
    private CurrenyEnum currency;
    private boolean activeForWithdraw;
    private boolean activeForShopping;
}
