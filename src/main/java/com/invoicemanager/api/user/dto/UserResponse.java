package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.enums.UserRole;

public record UserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    UserRole role
) {
}
