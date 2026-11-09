package com.invoicemanager.api.product.dto;

import com.invoicemanager.api.product.enums.ProductSortBy;
import com.invoicemanager.api.product.enums.ProductSortDirection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record SearchProductRequest(
        String name,
        BigDecimal unitPriceFrom,
        BigDecimal unitPriceTo,

        @Min(0)
        Integer page,

        @Min(1) @Max(100)
        Integer size,

        ProductSortBy sortBy,
        ProductSortDirection sortDirection
) {
    public SearchProductRequest {
        if(page == null) page = 0;
        if(size == null) size = 20;
        if(sortBy == null) sortBy = ProductSortBy.ID;
        if(sortDirection == null) sortDirection = ProductSortDirection.ASC;
    }
}
