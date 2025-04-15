package com.arif.demo.model.web.wallet;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChanceWithdrawStatusRequestDto {
    private boolean activeForWithdraw;
    @NotBlank
    private String walletName;
}
