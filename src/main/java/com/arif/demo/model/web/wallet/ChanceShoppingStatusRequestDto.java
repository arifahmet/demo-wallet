package com.arif.demo.model.web.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChanceShoppingStatusRequestDto {
    @NotBlank
    private boolean activeForShopping;
    @NotBlank
    @Size(max = 250)
    private String walletName;
}
