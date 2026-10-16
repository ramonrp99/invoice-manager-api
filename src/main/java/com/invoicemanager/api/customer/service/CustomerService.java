package com.invoicemanager.api.customer.service;

import com.invoicemanager.api.customer.dto.CustomerResponse;
import com.invoicemanager.api.customer.dto.CreateCustomerRequest;
import com.invoicemanager.api.customer.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> findAll();
    CustomerResponse findById(Long id);
    CustomerResponse create(CreateCustomerRequest req);
    CustomerResponse update(Long id, UpdateCustomerRequest req);
    void delete(Long id);
}
