package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.entity.Role;

import java.time.Instant;

public record AdminUserResponse(
    Long id,
    String name,
    String email,
    Role role,
    Instant created_at,
    boolean is_active
) {
}
