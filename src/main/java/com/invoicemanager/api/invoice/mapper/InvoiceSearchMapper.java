package com.invoicemanager.api.invoice.mapper;

import com.invoicemanager.api.invoice.dto.SearchInvoiceRequest;
import com.invoicemanager.api.invoice.model.InvoiceFilter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class InvoiceSearchMapper {

    public InvoiceFilter toFilter(SearchInvoiceRequest req) {
        return new InvoiceFilter(
                req.customer(),
                req.status(),
                req.issuedFrom(),
                req.issuedTo()
        );
    }

    public Pageable toPageable(SearchInvoiceRequest req) {
        return PageRequest.of(
                req.page(),
                req.size(),
                toSort(req)
        );
    }

    private Sort toSort(SearchInvoiceRequest req) {
        Sort.Direction direction = switch(req.sortDirection()) {
            case ASC -> Sort.Direction.ASC;
            case DESC -> Sort.Direction.DESC;
        };

        return switch(req.sortBy()) {
            case ID -> Sort.by(
                    new Sort.Order(direction, "id")
            );
            case ISSUE_DATE -> Sort.by(
                    new Sort.Order(direction, "issueDate")
            );
            case CUSTOMER -> Sort.by(
                    new Sort.Order(direction, "customer.lastName"),
                    new Sort.Order(direction, "customer.firstName")
            );
            case CREATED_AT -> Sort.by(
                    new Sort.Order(direction, "createdAt")
            );
        };
    }
}
