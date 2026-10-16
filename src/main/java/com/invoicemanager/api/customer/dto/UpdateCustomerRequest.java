package com.invoicemanager.api.customer.dto;

public record UpdateCustomerRequest(
    String firstName,
    String lastName,
    String nif,
    String street,
    Integer number,
    String numberLetter,
    String postalCode,
    String city
) {
}
