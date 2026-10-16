package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.entity.Role;

import java.time.Instant;

public record AdminUserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    Role role,
    Instant createdAt,
    boolean isActive
) {
}
