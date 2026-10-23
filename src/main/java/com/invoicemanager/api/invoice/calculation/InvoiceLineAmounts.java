package com.invoicemanager.api.invoice.calculation;

import java.math.BigDecimal;

public record InvoiceLineAmounts(
        BigDecimal subtotal,
        BigDecimal taxAmount,
        BigDecimal total
) {
}
