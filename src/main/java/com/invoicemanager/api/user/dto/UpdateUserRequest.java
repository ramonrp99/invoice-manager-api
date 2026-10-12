package com.invoicemanager.api.user.dto;

public record UpdateUserRequest(
    String name,
    String email,
    String password
) {
}
