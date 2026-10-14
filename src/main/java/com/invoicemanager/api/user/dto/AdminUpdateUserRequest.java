package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.entity.Role;

import jakarta.validation.constraints.Email;

public record AdminUpdateUserRequest(
    String name,
    @Email String email,
    Role role,
    Boolean is_active
) {
}
