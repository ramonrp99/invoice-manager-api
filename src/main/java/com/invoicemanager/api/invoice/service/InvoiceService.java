package com.invoicemanager.api.invoice.service;

import com.invoicemanager.api.invoice.dto.CreateInvoiceRequest;
import com.invoicemanager.api.invoice.dto.InvoiceResponse;
import com.invoicemanager.api.invoice.dto.UpdateInvoiceRequest;

import java.util.List;

public interface InvoiceService {

    List<InvoiceResponse> findAll();
    InvoiceResponse findById(Long id);
    InvoiceResponse create(CreateInvoiceRequest req);
    InvoiceResponse issue(Long id);
    InvoiceResponse cancel(Long id);
    InvoiceResponse update(Long id, UpdateInvoiceRequest req);
}
