package com.invoicemanager.api.invoice.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record InvoiceLineResponse(
        Long id,
        InvoiceLineProductResponse product,
        BigDecimal quantity,
        BigDecimal lineSubtotal,
        BigDecimal taxAmount,
        BigDecimal lineTotal,
        Instant createdAt
) {
}
