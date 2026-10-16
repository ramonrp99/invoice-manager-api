package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.entity.Role;

public record UserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    Role role
) {
}
