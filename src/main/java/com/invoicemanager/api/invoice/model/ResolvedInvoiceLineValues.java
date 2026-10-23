package com.invoicemanager.api.invoice.model;

import java.math.BigDecimal;

public record ResolvedInvoiceLineValues(
        String productName,
        BigDecimal unitPrice,
        BigDecimal subtotal,
        BigDecimal taxRate,
        BigDecimal taxAmount,
        BigDecimal total
) {
}
