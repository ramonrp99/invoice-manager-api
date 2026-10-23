package com.invoicemanager.api.invoice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateInvoiceLineRequest(
        Long id,

        @NotNull
        Long productId,

        @NotNull
        @Positive
        @Digits(integer = 10, fraction = 3)
        BigDecimal quantity
) {
}
