package com.invoicemanager.api.invoice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record UpdateInvoiceRequest(
        @NotNull
        Long customerId,

        @NotNull
        LocalDate periodStart,

        @NotNull
        LocalDate periodEnd,

        @NotEmpty
        @Valid
        List<UpdateInvoiceLineRequest> invoiceLines
) {
}
