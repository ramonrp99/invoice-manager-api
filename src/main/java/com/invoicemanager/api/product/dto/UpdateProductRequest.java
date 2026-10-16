package com.invoicemanager.api.product.dto;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        BigDecimal price,
        BigDecimal taxRate
) {
}
