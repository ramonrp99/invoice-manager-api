package com.invoicemanager.api.customer.service.impl;

import com.invoicemanager.api.common.exception.ApiError;
import com.invoicemanager.api.common.exception.ApiException;
import com.invoicemanager.api.customer.dto.CustomerResponse;
import com.invoicemanager.api.customer.dto.CreateCustomerRequest;
import com.invoicemanager.api.customer.dto.SearchCustomerRequest;
import com.invoicemanager.api.customer.dto.UpdateCustomerRequest;
import com.invoicemanager.api.customer.entity.Customer;
import com.invoicemanager.api.customer.mapper.CustomerMapper;
import com.invoicemanager.api.customer.mapper.CustomerSearchMapper;
import com.invoicemanager.api.customer.repository.CustomerRepository;
import com.invoicemanager.api.customer.service.CustomerService;

import com.invoicemanager.api.customer.specification.CustomerSpecification;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerSearchMapper customerSearchMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, CustomerSearchMapper customerSearchMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.customerSearchMapper = customerSearchMapper;
    }

    // GET
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> findAll(SearchCustomerRequest req) {
        Pageable pageable = customerSearchMapper.toPageable(req);
        Specification<Customer> specification = Specification
                .where(CustomerSpecification.containsName(req.name()))
                .and(CustomerSpecification.containsNif(req.nif()));

        Page<Customer> customers = customerRepository.findAll(specification, pageable);

        return customers.map(customerMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findById(@NonNull Long id) {
        return customerMapper.toResponse(findCustomer(id));
    }

    // POST
    @Override
    public CustomerResponse create(@NonNull CreateCustomerRequest req) {
        validateUniqueNif(req.nif());

        Customer customer = customerRepository.save(customerMapper.toEntity(req));

        return customerMapper.toResponse(customer);
    }

    // PUT
    @Override
    public CustomerResponse update(@NonNull Long id, @NonNull UpdateCustomerRequest req) {
        Customer customer = findCustomer(id);

        validateUniqueNif(req.nif());
        customerMapper.updateEntity(customer, req);

        Customer updated = customerRepository.save(customer);

        return customerMapper.toResponse(updated);
    }

    // DELETE
    @Override
    public void delete(@NonNull Long id) {
        if(!customerRepository.existsById(id)) {
            throw new ApiException(ApiError.CUSTOMER_NOT_FOUND);
        }

        customerRepository.deleteById(id);
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ApiException(ApiError.CUSTOMER_NOT_FOUND));
    }

    private void validateUniqueNif(String nif) {
        if(nif != null && customerRepository.existsByNif(nif)) {
            throw new ApiException(ApiError.CUSTOMER_NIF_ALREADY_EXISTS);
        }
    }
}
