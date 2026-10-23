package com.invoicemanager.api.invoice.calculation;

import java.math.BigDecimal;

public record InvoiceAmounts(
        BigDecimal subtotal,
        BigDecimal taxAmount,
        BigDecimal total
) {
}
