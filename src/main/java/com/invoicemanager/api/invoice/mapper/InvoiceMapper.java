package com.invoicemanager.api.invoice.mapper;

import com.invoicemanager.api.customer.entity.Customer;
import com.invoicemanager.api.invoice.dto.CreateInvoiceLineRequest;
import com.invoicemanager.api.invoice.dto.CreateInvoiceRequest;
import com.invoicemanager.api.invoice.dto.InvoiceLineResponse;
import com.invoicemanager.api.invoice.dto.InvoiceResponse;
import com.invoicemanager.api.invoice.entity.Invoice;
import com.invoicemanager.api.invoice.entity.InvoiceLine;
import com.invoicemanager.api.invoice.model.ResolvedInvoiceLineValues;
import com.invoicemanager.api.invoice.model.ResolvedInvoiceValues;
import com.invoicemanager.api.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
                invoice.getCanceledAt(),
                invoiceLines
        );
    }

    public Invoice toEntity(CreateInvoiceRequest req, Customer customer, Map<Long, Product> products) {
        Invoice invoice = new Invoice();

        invoice.setCustomer(customer);
        invoice.setPeriodStart(req.periodStart());
        invoice.setPeriodEnd(req.periodEnd());

        for(CreateInvoiceLineRequest lineReq : req.invoiceLines()) {
            InvoiceLine invoiceLine = toLineEntity(lineReq, products.get(lineReq.productId()));

            invoice.addInvoiceLine(invoiceLine);
        }

        return invoice;
    }

    private InvoiceLineResponse toLineResponse(InvoiceLine invoiceLine, ResolvedInvoiceLineValues values) {
        return new InvoiceLineResponse(
                invoiceLine.getId(),
                invoiceLine.getProduct().getId(),
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

    private InvoiceLine toLineEntity(CreateInvoiceLineRequest req, Product product) {
        InvoiceLine invoiceLine = new InvoiceLine();

        invoiceLine.setProduct(product);
        invoiceLine.setQuantity(req.quantity());

        return invoiceLine;
    }
}
