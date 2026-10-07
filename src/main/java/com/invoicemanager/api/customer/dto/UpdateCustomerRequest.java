package com.invoicemanager.api.customer.dto;

public record UpdateCustomerRequest(
    String name,
    String nif,
    String address
) {
}
