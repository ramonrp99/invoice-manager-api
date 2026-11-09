package com.invoicemanager.api.customer.service;

import com.invoicemanager.api.customer.dto.CustomerResponse;
import com.invoicemanager.api.customer.dto.CreateCustomerRequest;
import com.invoicemanager.api.customer.dto.SearchCustomerRequest;
import com.invoicemanager.api.customer.dto.UpdateCustomerRequest;
import org.springframework.data.domain.Page;

public interface CustomerService {

    Page<CustomerResponse> findAll(SearchCustomerRequest req);
    CustomerResponse findById(Long id);
    CustomerResponse create(CreateCustomerRequest req);
    CustomerResponse update(Long id, UpdateCustomerRequest req);
    void delete(Long id);
}
