package com.invoicemanager.api.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
    @NotBlank String name,
    @NotBlank String nif,
    @NotBlank String address
) {
}
