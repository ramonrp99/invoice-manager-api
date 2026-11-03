package com.invoicemanager.api.invoice.model;

import com.invoicemanager.api.invoice.enums.InvoiceStatus;

import java.time.LocalDate;

public record InvoiceFilter(
        String customer,
        InvoiceStatus status,
        LocalDate issuedFrom,
        LocalDate issuedTo
) {
}
