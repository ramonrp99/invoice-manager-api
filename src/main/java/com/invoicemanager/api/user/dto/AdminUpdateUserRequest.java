package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.enums.UserRole;

import jakarta.validation.constraints.Email;

public record AdminUpdateUserRequest(
    String firstName,
    String lastName,
    @Email String email,
    UserRole role,
    Boolean isActive
) {
}
