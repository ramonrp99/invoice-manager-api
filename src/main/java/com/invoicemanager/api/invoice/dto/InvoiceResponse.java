package com.invoicemanager.api.invoice.dto;

import com.invoicemanager.api.invoice.entity.InvoiceStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record InvoiceResponse(
        Long id,
        String number,
        Long customerId,
        InvoiceStatus status,
        LocalDate issueDate,
        LocalDate periodStart,
        LocalDate periodEnd,
        BigDecimal subtotal,
        BigDecimal taxTotal,
        BigDecimal total,
        Instant createdAt,
        Instant issuedAt,
        List<InvoiceLineResponse> invoiceLines
) {
}
