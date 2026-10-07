package com.invoicemanager.api.customer.dto;

public record CustomerResponse(
    Long id,
    String name,
    String nif,
    String address
) {
}
