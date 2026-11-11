package com.invoicemanager.api.product.mapper;

import com.invoicemanager.api.product.dto.SearchProductRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class ProductSearchMapper {

    public Pageable toPageable(SearchProductRequest req) {
        return PageRequest.of(
                req.page(),
                req.size(),
                toSort(req)
        );
    }

    private Sort toSort(SearchProductRequest req) {
        Sort.Direction direction = switch(req.sortDirection()) {
            case ASC -> Sort.Direction.ASC;
            case DESC -> Sort.Direction.DESC;
        };

        return switch(req.sortBy()) {
            case ID -> Sort.by(
                    new Sort.Order(direction, "id")
            );
            case NAME -> Sort.by(
                    new Sort.Order(direction, "name")
            );
            case UNIT_PRICE -> Sort.by(
                    new Sort.Order(direction, "unitPrice")
            );
            case CREATED_AT -> Sort.by(
                    new Sort.Order(direction, "createdAt")
            );
        };
    }
}
