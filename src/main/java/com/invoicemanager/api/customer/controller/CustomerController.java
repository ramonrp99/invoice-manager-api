package com.invoicemanager.api.customer.controller;

import com.invoicemanager.api.customer.dto.CustomerResponse;
import com.invoicemanager.api.customer.dto.CreateCustomerRequest;
import com.invoicemanager.api.customer.dto.UpdateCustomerRequest;
import com.invoicemanager.api.customer.service.CustomerService;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponse> getCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable @NonNull Long id) {
        return customerService.findById(id);
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
