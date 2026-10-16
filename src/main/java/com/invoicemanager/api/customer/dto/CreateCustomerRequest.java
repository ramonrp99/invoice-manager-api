package com.invoicemanager.api.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String nif,
    @NotBlank String street,
    @NotBlank Integer number,
    @NotBlank String numberLetter,
    @NotBlank String postalCode,
    @NotBlank String city
) {
}
