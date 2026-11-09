package com.invoicemanager.api.product.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal unitPrice,
        BigDecimal taxRate,
        Instant createdAt
) {
}
