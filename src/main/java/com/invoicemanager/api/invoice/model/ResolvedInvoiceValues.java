package com.invoicemanager.api.invoice.model;

import java.math.BigDecimal;
import java.util.Map;

public record ResolvedInvoiceValues(
        BigDecimal subtotal,
        BigDecimal taxAmount,
        BigDecimal total,
        Map<Long, ResolvedInvoiceLineValues> resolvedInvoiceLinesValues
) {
}
