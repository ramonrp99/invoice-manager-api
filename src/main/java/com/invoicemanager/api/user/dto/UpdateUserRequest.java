package com.invoicemanager.api.user.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserRequest(
    String name,
    @Email String email
) {
}
