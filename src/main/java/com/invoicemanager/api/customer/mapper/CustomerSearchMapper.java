package com.invoicemanager.api.customer.mapper;

import com.invoicemanager.api.customer.dto.SearchCustomerRequest;
import com.invoicemanager.api.customer.model.CustomerFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class CustomerSearchMapper {

    public CustomerFilter toFilter(SearchCustomerRequest req) {
        return new CustomerFilter(
                req.name(),
                req.nif()
        );
    }

    public Pageable toPageable(SearchCustomerRequest req) {
        return PageRequest.of(
                req.page(),
                req.size(),
                toSort(req)
        );
    }

    private Sort toSort(SearchCustomerRequest req) {
        Sort.Direction direction = switch(req.sortDirection()) {
            case ASC -> Sort.Direction.ASC;
            case DESC -> Sort.Direction.DESC;
        };

        return switch(req.sortBy()) {
            case ID -> Sort.by(
                    new Sort.Order(direction, "id")
            );
            case NAME -> Sort.by(
                    new Sort.Order(direction, "lastName"),
                    new Sort.Order(direction, "firstName")
            );
            case CREATED_AT -> Sort.by(
                    new Sort.Order(direction, "createdAt")
            );
        };
    }
}
