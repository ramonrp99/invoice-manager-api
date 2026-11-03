package com.invoicemanager.api.invoice.dto;

import java.math.BigDecimal;

public record InvoiceLineProductResponse(
        Long id,
        String name,
        BigDecimal unitPrice,
        BigDecimal taxRate
) {
}
