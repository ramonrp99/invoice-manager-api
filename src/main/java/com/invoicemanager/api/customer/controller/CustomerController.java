package com.invoicemanager.api.customer.controller;

import com.invoicemanager.api.customer.dto.CustomerResponse;
import com.invoicemanager.api.customer.dto.CreateCustomerRequest;
import com.invoicemanager.api.customer.dto.SearchCustomerRequest;
import com.invoicemanager.api.customer.dto.UpdateCustomerRequest;
import com.invoicemanager.api.customer.service.CustomerService;

import com.invoicemanager.api.invoice.dto.InvoiceResponse;
import com.invoicemanager.api.invoice.dto.SearchInvoiceRequest;
import com.invoicemanager.api.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final InvoiceService invoiceService;

    public CustomerController(CustomerService customerService, InvoiceService invoiceService) {
        this.customerService = customerService;
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public Page<CustomerResponse> getCustomers(@Valid SearchCustomerRequest req) {
        return customerService.findAll(req);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable @NonNull Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/{id}/invoices")
    public Page<InvoiceResponse> getCustomerInvoices(@PathVariable @NonNull Long id, SearchInvoiceRequest req) {
        return invoiceService.findByCustomerId(id, req);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody @NonNull CreateCustomerRequest req) {
        return customerService.create(req);
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable @NonNull Long id, @RequestBody @NonNull UpdateCustomerRequest req) {
        return customerService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable @NonNull Long id) {
        customerService.delete(id);
    }
}
