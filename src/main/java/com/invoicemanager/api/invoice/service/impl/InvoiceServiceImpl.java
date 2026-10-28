package com.invoicemanager.api.invoice.service.impl;

import com.invoicemanager.api.customer.entity.Customer;
import com.invoicemanager.api.customer.repository.CustomerRepository;
import com.invoicemanager.api.invoice.calculation.*;
import com.invoicemanager.api.invoice.dto.CreateInvoiceLineRequest;
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
import com.invoicemanager.api.product.entity.Product;
import com.invoicemanager.api.product.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final InvoiceMapper invoiceMapper;
    private final InvoiceCalculator invoiceCalculator;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, ProductRepository productRepository, InvoiceMapper invoiceMapper, InvoiceCalculator invoiceCalculator) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;

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
        Invoice invoice = findInvoice(id);

        return invoiceMapper.toResponse(invoice, resolveInvoiceValues(invoice));

    }

    @Override
    @Transactional
    public InvoiceResponse create(CreateInvoiceRequest req) {
        Customer customer = customerRepository.findById(req.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado."));

        Set<Long> productIds = req.invoiceLines().stream()
                .map(CreateInvoiceLineRequest::productId)
                .collect(Collectors.toSet());

        Map<Long, Product> products = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        if(products.size() != productIds.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado.");
        }

        Invoice invoice = invoiceRepository.save(invoiceMapper.toEntity(req, customer, products));

        return invoiceMapper.toResponse(invoice, resolveInvoiceValues(invoice));
    }

    @Override
    @Transactional
    public InvoiceResponse issue(Long id) {
        Invoice invoice = findInvoice(id);
        ResolvedInvoiceValues calculatedInvoiceValues = calculateInvoiceValues(invoice);

        invoice.setSeries(String.valueOf(LocalDate.now().getYear()));
        invoice.setStatus(InvoiceStatus.ISSUED);
        invoice.setIssueDate(LocalDate.now());
        invoice.setSubtotal(calculatedInvoiceValues.subtotal());
        invoice.setTaxAmount(calculatedInvoiceValues.taxAmount());
        invoice.setTotal(calculatedInvoiceValues.total());
        invoice.setIssuedAt(Instant.now());

        for(InvoiceLine line : invoice.getInvoiceLines()) {
            ResolvedInvoiceLineValues calculatedInvoiceLineValues = calculatedInvoiceValues.resolvedInvoiceLinesValues().get(line.getId());

            line.setNameSnapshot(calculatedInvoiceLineValues.productName());
            line.setUnitPriceSnapshot(calculatedInvoiceLineValues.unitPrice());
            line.setLineSubtotalSnapshot(calculatedInvoiceLineValues.subtotal());
            line.setTaxRateSnapshot(calculatedInvoiceLineValues.taxRate());
            line.setTaxAmountSnapshot(calculatedInvoiceLineValues.taxAmount());
            line.setLineTotalSnapshot(calculatedInvoiceLineValues.total());
        }

        Invoice issued = invoiceRepository.save(invoice);

        return invoiceMapper.toResponse(issued, resolveInvoiceValues(issued));
    }

    @Override
    public InvoiceResponse cancel(Long id) {
        return null;
    }

    @Override
    public InvoiceResponse update(Long id, UpdateInvoiceRequest req) {
        return null;
    }

    private ResolvedInvoiceValues calculateInvoiceValues(Invoice invoice) {
        List<InvoiceLineAmounts> linesAmounts = new ArrayList<>();
        Map<Long, ResolvedInvoiceLineValues> resolvedInvoiceLinesValues = new HashMap<>();

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

        return new ResolvedInvoiceValues(
                invoiceAmounts.subtotal(),
                invoiceAmounts.taxAmount(),
                invoiceAmounts.total(),
                resolvedInvoiceLinesValues
        );
    }

    private ResolvedInvoiceValues storedInvoiceValues(Invoice invoice) {
        Map<Long, ResolvedInvoiceLineValues> resolvedInvoiceLinesValues = new HashMap<>();

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

        return new ResolvedInvoiceValues(
                invoice.getSubtotal(),
                invoice.getTaxAmount(),
                invoice.getTotal(),
                resolvedInvoiceLinesValues
        );
    }

    private ResolvedInvoiceValues resolveInvoiceValues(Invoice invoice) {
        if(invoice.getStatus().equals(InvoiceStatus.DRAFT)) {
            return calculateInvoiceValues(invoice);
        } else {
            return storedInvoiceValues(invoice);
        }
    }

    private Invoice findInvoice(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada."));
    }
}
