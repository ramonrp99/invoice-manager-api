package com.invoicemanager.api.invoice.service;

import com.invoicemanager.api.invoice.dto.*;
import org.springframework.data.domain.Page;

public interface InvoiceService {

    Page<InvoiceResponse> findAll(SearchInvoiceRequest req);
    InvoiceResponse findById(Long id);
    Page<InvoiceResponse> findByCustomerId(Long id, SearchInvoiceRequest req);
    InvoiceResponse create(CreateInvoiceRequest req);
    InvoiceResponse issue(Long id);
    InvoiceResponse cancel(Long id);
    InvoiceResponse update(Long id, UpdateInvoiceRequest req);
}
