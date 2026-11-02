package com.invoicemanager.api.invoice.service;

import com.invoicemanager.api.invoice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {

    Page<InvoiceResponse> findAll(InvoiceFilter filter, Pageable pageable);
    InvoiceResponse findById(Long id);
    InvoiceResponse create(CreateInvoiceRequest req);
    InvoiceResponse issue(Long id);
    InvoiceResponse cancel(Long id);
    InvoiceResponse update(Long id, UpdateInvoiceRequest req);
}
