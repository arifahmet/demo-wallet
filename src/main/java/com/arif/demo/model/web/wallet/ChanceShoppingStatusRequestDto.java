package com.arif.demo.model.web.wallet;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChanceShoppingStatusRequestDto {
    private boolean activeForShopping;
    @NotBlank
    private String walletName;
}
