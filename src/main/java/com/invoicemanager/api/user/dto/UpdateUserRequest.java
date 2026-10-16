package com.invoicemanager.api.user.dto;

import jakarta.validation.constraints.Email;

public record UpdateUserRequest(
    String firstName,
    String lastName,
    @Email String email
) {
}
