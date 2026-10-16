package com.invoicemanager.api.product.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank String name,
        @NotBlank BigDecimal price,
        @NotBlank BigDecimal taxRate
) {
}
