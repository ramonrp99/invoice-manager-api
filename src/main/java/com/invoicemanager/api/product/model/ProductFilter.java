package com.invoicemanager.api.product.model;

import java.math.BigDecimal;

public record ProductFilter(
        String name,
        BigDecimal unitPriceFrom,
        BigDecimal unitPriceTo
) {
}
