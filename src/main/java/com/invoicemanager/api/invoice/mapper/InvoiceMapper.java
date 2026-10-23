package com.invoicemanager.api.invoice.mapper;

import com.invoicemanager.api.invoice.dto.InvoiceLineResponse;
import com.invoicemanager.api.invoice.dto.InvoiceResponse;
import com.invoicemanager.api.invoice.entity.Invoice;
import com.invoicemanager.api.invoice.entity.InvoiceLine;
import com.invoicemanager.api.invoice.model.ResolvedInvoiceLineValues;
import com.invoicemanager.api.invoice.model.ResolvedInvoiceValues;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvoiceMapper {

    public InvoiceResponse toResponse(Invoice invoice, ResolvedInvoiceValues resolvedInvoiceValues) {
        String invoiceNumber = invoice.getSeries() == null || invoice.getNumber() == null
                ? null
                : invoice.getNumber() + "/" + invoice.getSeries();

        List<InvoiceLineResponse> invoiceLines = invoice.getInvoiceLines().stream()
                .map(line ->
                        toLineResponse(line, resolvedInvoiceValues.resolvedInvoiceLinesValues().get(line.getId())))
                .toList();

        return new InvoiceResponse(
                invoice.getId(),
                invoiceNumber,
                invoice.getCustomer().getId(),
                invoice.getStatus(),
                invoice.getIssueDate(),
                invoice.getPeriodStart(),
                invoice.getPeriodEnd(),
                resolvedInvoiceValues.subtotal(),
                resolvedInvoiceValues.taxAmount(),
                resolvedInvoiceValues.total(),
                invoice.getCreatedAt(),
                invoice.getIssuedAt(),
                invoiceLines
        );
    }

    public InvoiceLineResponse toLineResponse(InvoiceLine invoiceLine, ResolvedInvoiceLineValues values) {
        return new InvoiceLineResponse(
                invoiceLine.getId(),
                invoiceLine.getProduct().getId(),
                invoiceLine.getLineNumber(),
                values.productName(),
                invoiceLine.getQuantity(),
                values.unitPrice(),
                values.subtotal(),
                values.taxRate(),
                values.taxAmount(),
                values.total(),
                invoiceLine.getCreatedAt()
        );
    }
}
