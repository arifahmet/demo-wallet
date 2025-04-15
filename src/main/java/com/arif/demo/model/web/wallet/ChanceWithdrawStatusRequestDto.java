package com.arif.demo.model.web.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChanceWithdrawStatusRequestDto {
    private boolean activeForWithdraw;
    @NotBlank
    @Size(max = 250)
    private String walletName;
}
