package com.invoicemanager.api.customer.dto;

import com.invoicemanager.api.customer.enums.CustomerSortBy;
import com.invoicemanager.api.customer.enums.CustomerSortDirection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record SearchCustomerRequest(
        String name,
        String nif,

        @Min(0)
        Integer page,

        @Min(1) @Max(100)
        Integer size,

        CustomerSortBy sortBy,
        CustomerSortDirection sortDirection
) {
    public SearchCustomerRequest {
        if(page == null) page = 0;
        if(size == null) size = 20;
        if(sortBy == null) sortBy = CustomerSortBy.ID;
        if(sortDirection == null) sortDirection = CustomerSortDirection.ASC;
    }
}
