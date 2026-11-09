package com.invoicemanager.api.user.model;

import com.invoicemanager.api.user.enums.UserRole;

public record AdminUserFilter(
        String name,
        String email,
        UserRole role,
        Boolean isActive
) {
}
