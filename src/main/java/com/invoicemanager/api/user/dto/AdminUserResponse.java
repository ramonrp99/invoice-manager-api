package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.enums.UserRole;

import java.time.Instant;

public record AdminUserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    UserRole role,
    Instant createdAt,
    boolean isActive
) {
}
