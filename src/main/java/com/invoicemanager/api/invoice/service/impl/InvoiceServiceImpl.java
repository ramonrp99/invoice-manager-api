package com.invoicemanager.api.invoice.service.impl;

import com.invoicemanager.api.invoice.calculation.*;
import com.invoicemanager.api.invoice.dto.CreateInvoiceRequest;
import com.invoicemanager.api.invoice.dto.InvoiceResponse;
import com.invoicemanager.api.invoice.dto.UpdateInvoiceRequest;
import com.invoicemanager.api.invoice.entity.Invoice;
import com.invoicemanager.api.invoice.entity.InvoiceLine;
import com.invoicemanager.api.invoice.entity.InvoiceStatus;
import com.invoicemanager.api.invoice.mapper.InvoiceMapper;
import com.invoicemanager.api.invoice.model.ResolvedInvoiceLineValues;
import com.invoicemanager.api.invoice.model.ResolvedInvoiceValues;
import com.invoicemanager.api.invoice.repository.InvoiceRepository;
import com.invoicemanager.api.invoice.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final InvoiceCalculator invoiceCalculator;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, InvoiceCalculator invoiceCalculator) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.invoiceCalculator = invoiceCalculator;
    }

    @Override
    public List<InvoiceResponse> findAll() {
        List<Invoice> invoices = invoiceRepository.findAll();

        return invoices.stream()
                .map(inv -> invoiceMapper.toResponse(inv, resolveInvoiceValues(inv)))
                .toList();
    }

    @Override
    public InvoiceResponse findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada."));

        return invoiceMapper.toResponse(invoice, resolveInvoiceValues(invoice));

    }

    @Override
    public InvoiceResponse create(CreateInvoiceRequest req) {
        return null;
    }

    @Override
    public InvoiceResponse issue(Long id) {
        return null;
    }

    @Override
    public InvoiceResponse cancel(Long id) {
        return null;
    }

    @Override
    public InvoiceResponse update(Long id, UpdateInvoiceRequest req) {
        return null;
    }

    private ResolvedInvoiceValues resolveInvoiceValues(Invoice invoice) {
        ResolvedInvoiceValues resolvedInvoiceValues;
        Map<Long, ResolvedInvoiceLineValues> resolvedInvoiceLinesValues = new HashMap<>();

        if(invoice.getStatus().equals(InvoiceStatus.DRAFT)) {
            List<InvoiceLineAmounts> linesAmounts = new ArrayList<>();

            for(InvoiceLine line : invoice.getInvoiceLines()) {
                InvoiceLineAmounts invoiceLineAmounts = invoiceCalculator.calculateLine(
                        line.getQuantity(),
                        line.getProduct().getPrice(),
                        line.getProduct().getTaxRate()
                );

                linesAmounts.add(invoiceLineAmounts);

                ResolvedInvoiceLineValues lineValues = new ResolvedInvoiceLineValues(
                        line.getProduct().getName(),
                        line.getProduct().getPrice(),
                        invoiceLineAmounts.subtotal(),
                        line.getProduct().getTaxRate(),
                        invoiceLineAmounts.taxAmount(),
                        invoiceLineAmounts.total()
                );

                resolvedInvoiceLinesValues.put(line.getId(), lineValues);
            }

            InvoiceAmounts invoiceAmounts = invoiceCalculator.calculateInvoice(linesAmounts);

            resolvedInvoiceValues = new ResolvedInvoiceValues(
                    invoiceAmounts.subtotal(),
                    invoiceAmounts.taxAmount(),
                    invoiceAmounts.total(),
                    resolvedInvoiceLinesValues
            );
        } else {
            for(InvoiceLine line : invoice.getInvoiceLines()) {
                ResolvedInvoiceLineValues lineValues = new ResolvedInvoiceLineValues(
                        line.getNameSnapshot(),
                        line.getUnitPriceSnapshot(),
                        line.getLineSubtotalSnapshot(),
                        line.getTaxRateSnapshot(),
                        line.getTaxAmountSnapshot(),
                        line.getLineTotalSnapshot()
                );

                resolvedInvoiceLinesValues.put(line.getId(), lineValues);
            }

            resolvedInvoiceValues = new ResolvedInvoiceValues(
                    invoice.getSubtotal(),
                    invoice.getTaxAmount(),
                    invoice.getTotal(),
                    resolvedInvoiceLinesValues
            );
        }

        return resolvedInvoiceValues;
    }
}
