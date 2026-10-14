package com.invoicemanager.api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminCreateUserRequest(
    @NotBlank String name,
    @NotBlank @Email String email,
    @NotBlank String password
) {
}
