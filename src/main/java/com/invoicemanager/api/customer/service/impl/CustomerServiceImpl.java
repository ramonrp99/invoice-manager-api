package com.invoicemanager.api.customer.service.impl;

import com.invoicemanager.api.customer.dto.CustomerResponse;
import com.invoicemanager.api.customer.dto.CreateCustomerRequest;
import com.invoicemanager.api.customer.dto.UpdateCustomerRequest;
import com.invoicemanager.api.customer.entity.Customer;
import com.invoicemanager.api.customer.mapper.CustomerMapper;
import com.invoicemanager.api.customer.repository.CustomerRepository;
import com.invoicemanager.api.customer.service.CustomerService;

import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    // GET
    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(customerMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findById(@NonNull Long id) {
        return customerMapper.toResponse(customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado.")));
    }

    // POST
    @Override
    public CustomerResponse create(@NonNull CreateCustomerRequest req) {
        if(customerRepository.existsByNif(req.nif())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con ese NIF.");
        }

        Customer customer = customerRepository.save(customerMapper.toEntity(req));

        return customerMapper.toResponse(customer);
    }

    // PUT
    @Override
    public CustomerResponse update(@NonNull Long id, @NonNull UpdateCustomerRequest req) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado."));

        if(customerRepository.existsByNif(req.nif())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con ese NIF.");
        }

        customerMapper.updateEntity(customer, req);

        Customer updated = customerRepository.save(customer);

        return customerMapper.toResponse(updated);
    }

    // DELETE
    @Override
    public void delete(@NonNull Long id) {
        if(!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado.");
        }

        customerRepository.deleteById(id);
    }
}
