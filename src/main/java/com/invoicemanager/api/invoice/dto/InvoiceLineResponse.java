package com.invoicemanager.api.invoice.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record InvoiceLineResponse(
        Long id,
        Long productId,
        String productName,
        BigDecimal quantity,
        BigDecimal unitPrice,
        BigDecimal lineSubtotal,
        BigDecimal taxRate,
        BigDecimal taxAmount,
        BigDecimal lineTotal,
        Instant createdAt
) {
}
