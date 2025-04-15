package com.arif.demo.model.web.credential;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequestDto(@NotBlank String username, @Size(min = 6, max = 6) String password,
                               @NotBlank String name, @NotBlank String surname, @Size(min = 11, max = 11) String tckn) {
}
