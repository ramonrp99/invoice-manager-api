package com.invoicemanager.api.invoice.controller;

import com.invoicemanager.api.invoice.dto.CreateInvoiceRequest;
import com.invoicemanager.api.invoice.dto.InvoiceResponse;
import com.invoicemanager.api.invoice.dto.UpdateInvoiceRequest;
import com.invoicemanager.api.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<InvoiceResponse> getInvoices() {
        return invoiceService.findAll();
    }

    @GetMapping("/{id}")
    public InvoiceResponse getInvoice(@PathVariable Long id) {
        return invoiceService.findById(id);
    }

    @PostMapping
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
