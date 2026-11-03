package com.invoicemanager.api.invoice.dto;

import com.invoicemanager.api.invoice.enums.InvoiceSortBy;
import com.invoicemanager.api.invoice.enums.InvoiceSortDirection;
import com.invoicemanager.api.invoice.enums.InvoiceStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public record SearchInvoiceRequest(
        String customer,
        InvoiceStatus status,
        LocalDate issuedFrom,
        LocalDate issuedTo,

        @Min(0)
        Integer page,

        @Min(1) @Max(100)
        Integer size,

        InvoiceSortBy sortBy,
        InvoiceSortDirection sortDirection
) {
    public SearchInvoiceRequest {
        if(page == null) page = 0;
        if(size == null) size = 20;
        if(sortBy == null) sortBy = InvoiceSortBy.ID;
        if(sortDirection == null) sortDirection = InvoiceSortDirection.ASC;
    }
}
