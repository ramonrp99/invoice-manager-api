package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.entity.Role;

import jakarta.validation.constraints.Email;

public record AdminUpdateUserRequest(
    String firstName,
    String lastName,
    @Email String email,
    Role role,
    Boolean isActive
) {
}
