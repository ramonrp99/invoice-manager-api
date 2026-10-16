package com.invoicemanager.api.customer.mapper;

import com.invoicemanager.api.customer.dto.CustomerResponse;
import com.invoicemanager.api.customer.dto.CreateCustomerRequest;
import com.invoicemanager.api.customer.dto.UpdateCustomerRequest;
import com.invoicemanager.api.customer.entity.Customer;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getNif(),
                customer.getStreet(),
                customer.getNumber(),
                customer.getNumberLetter(),
                customer.getPostalCode(),
                customer.getCity()
        );
    }

    public Customer toEntity(CreateCustomerRequest req) {
        Customer customer = new Customer();

        customer.setFirstName(req.firstName());
        customer.setLastName(req.lastName());
        customer.setNif(req.nif());
        customer.setStreet(req.street());
        customer.setNumber(req.number());
        customer.setNumberLetter(req.numberLetter());
        customer.setPostalCode(req.postalCode());
        customer.setCity(req.city());

        return customer;
    }

    public void updateEntity(Customer customer, UpdateCustomerRequest req) {
        if(req.firstName() != null) customer.setFirstName(req.firstName());
        if(req.lastName() != null) customer.setLastName(req.lastName());
        if(req.nif() != null) customer.setNif(req.nif());
        if(req.street() != null) customer.setStreet(req.street());
        if(req.number() != null) customer.setNumber(req.number());
        if(req.numberLetter() != null) customer.setNumberLetter(req.numberLetter());
        if(req.postalCode() != null) customer.setPostalCode(req.postalCode());
        if(req.city() != null) customer.setCity(req.city());
    }
}
