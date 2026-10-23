package com.invoicemanager.api.invoice.calculation;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class InvoiceCalculator {

    public InvoiceLineAmounts calculateLine(BigDecimal quantity, BigDecimal unitPrice, BigDecimal taxRate) {
        BigDecimal subtotal = quantity
                .multiply(unitPrice)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal taxAmount = subtotal
                .multiply(taxRate)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal total = subtotal.add(taxAmount)
                .setScale(2, RoundingMode.HALF_UP);

        return new InvoiceLineAmounts(subtotal, taxAmount, total);
    }

    public InvoiceAmounts calculateInvoice(List<InvoiceLineAmounts> linesAmounts) {
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal taxAmount = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;

        for(InvoiceLineAmounts line : linesAmounts) {
            subtotal = subtotal.add(line.subtotal());
            taxAmount = taxAmount.add(line.taxAmount());
            total = total.add(line.total());
        }

        return new InvoiceAmounts(
                subtotal.setScale(2, RoundingMode.HALF_UP),
                taxAmount.setScale(2, RoundingMode.HALF_UP),
                total.setScale(2, RoundingMode.HALF_UP)
        );
    }
}
