package com.arif.demo.model.web.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateWalletRequestDto {
    @NotBlank
    @Size(max = 250)
    private String walletName;
    @NotNull
    @Size(max = 5)
    private CurrenyEnum currency;
    private boolean activeForWithdraw;
    private boolean activeForShopping;
}
