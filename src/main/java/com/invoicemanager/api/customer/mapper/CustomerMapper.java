package com.invoicemanager.api.customer.mapper;

import com.invoicemanager.api.customer.dto.CustomerResponse;
import com.invoicemanager.api.customer.dto.CreateCustomerRequest;
import com.invoicemanager.api.customer.dto.UpdateCustomerRequest;
import com.invoicemanager.api.customer.entity.Customer;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getNif(), customer.getAddress());
    }

    public Customer toEntity(CreateCustomerRequest req) {
        Customer customer = new Customer();

        customer.setName(req.name());
        customer.setNif(req.nif());
        customer.setAddress(req.address());

        return customer;
    }

    public void updateEntity(Customer customer, UpdateCustomerRequest req) {
        if(req.name() != null) customer.setName(req.name());
        if(req.nif() != null) customer.setNif(req.nif());
        if(req.address() != null) customer.setAddress(req.address());
    }
}
