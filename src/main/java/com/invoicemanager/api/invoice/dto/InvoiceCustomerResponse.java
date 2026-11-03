package com.invoicemanager.api.invoice.dto;

public record InvoiceCustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String nif
) {
}
