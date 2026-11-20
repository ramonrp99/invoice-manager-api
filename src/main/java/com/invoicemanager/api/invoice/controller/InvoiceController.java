package com.invoicemanager.api.invoice.controller;

import com.invoicemanager.api.invoice.dto.*;
import com.invoicemanager.api.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.invoicemanager.api.config.ApiConfig.API_BASE_PATH;

@RestController
@RequestMapping(API_BASE_PATH + "/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public Page<InvoiceResponse> getInvoices(@Valid SearchInvoiceRequest req) {
        return invoiceService.findAll(req);
    }

    @GetMapping("/{id}")
    public InvoiceResponse getInvoice(@PathVariable Long id) {
        return invoiceService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceResponse createInvoice(@RequestBody @Valid CreateInvoiceRequest req) {
        return invoiceService.create(req);
    }

    @PostMapping("/{id}/issue")
    public InvoiceResponse issueInvoice(@PathVariable Long id) {
        return invoiceService.issue(id);
    }

    @PostMapping("/{id}/cancel")
    public InvoiceResponse cancelInvoice(@PathVariable Long id) {
        return invoiceService.cancel(id);
    }

    @PutMapping("/{id}")
    public InvoiceResponse updateInvoice(@PathVariable Long id, @RequestBody @Valid UpdateInvoiceRequest req) {
        return invoiceService.update(id, req);
    }
}
