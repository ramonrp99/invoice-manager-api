package com.invoicemanager.api.invoice.dto;

import com.invoicemanager.api.invoice.entity.InvoiceStatus;

import java.time.LocalDate;

public record InvoiceFilter(
        String customer,
        InvoiceStatus status,
        LocalDate issuedFrom,
        LocalDate issuedTo
) {
}
