package com.invoicemanager.api.user.dto;

import com.invoicemanager.api.user.entity.Role;

public record UserResponse(
    Long id,
    String name,
    String email,
    Role role
) {
}
