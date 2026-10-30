package com.invoicemanager.api.invoice.mapper;

import com.invoicemanager.api.customer.entity.Customer;
import com.invoicemanager.api.invoice.dto.*;
import com.invoicemanager.api.invoice.entity.Invoice;
import com.invoicemanager.api.invoice.entity.InvoiceLine;
import com.invoicemanager.api.invoice.model.ResolvedInvoiceLineValues;
import com.invoicemanager.api.invoice.model.ResolvedInvoiceValues;
import com.invoicemanager.api.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public void updateEntity(Invoice invoice, UpdateInvoiceRequest req, Customer customer, Map<Long, Product> products) {
        if(req.customerId() != null) invoice.setCustomer(customer);
        if(req.periodStart() != null) invoice.setPeriodStart(req.periodStart());
        if(req.periodEnd() != null) invoice.setPeriodEnd(req.periodEnd());

        // Map con todas las lineas que tiene la factura
        Map<Long, InvoiceLine> invoiceLinesMap = invoice.getInvoiceLines().stream()
                .collect(Collectors.toMap(InvoiceLine::getId, Function.identity()));

        // Recorre las lineas recibidas en la peticion
        // id == null -> Añadir nueva linea
        // id != null -> Modificar linea
        for(UpdateInvoiceLineRequest reqLine : req.invoiceLines()) {
            if(reqLine.id() == null) {
                InvoiceLine newLine = toLineEntity(
                        new CreateInvoiceLineRequest(
                                reqLine.productId(),
                                reqLine.quantity()
                        ),
                        products.get(reqLine.productId())
                );

                invoice.addInvoiceLine(newLine);
            } else {
                // Quitar del map las lineas actualizadas
                InvoiceLine line = invoiceLinesMap.remove(reqLine.id());

                if(line != null) {
                    updateLineEntity(line, reqLine, products.get(reqLine.productId()));
                }
            }
        }

        // Se eliminan las lineas restantes en el map (existen en la factura pero no se han recibido en la peticion)
        invoiceLinesMap.values().forEach(invoice::removeInvoiceLine);
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

    private void updateLineEntity(InvoiceLine invoiceLine, UpdateInvoiceLineRequest req, Product product) {
        if(req.productId() != null) invoiceLine.setProduct(product);
        if(req.quantity() != null) invoiceLine.setQuantity(req.quantity());
    }
}
