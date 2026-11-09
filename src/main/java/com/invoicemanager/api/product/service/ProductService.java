package com.invoicemanager.api.product.service;

import com.invoicemanager.api.product.dto.CreateProductRequest;
import com.invoicemanager.api.product.dto.ProductResponse;
import com.invoicemanager.api.product.dto.SearchProductRequest;
import com.invoicemanager.api.product.dto.UpdateProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductResponse> findAll(SearchProductRequest req);
    ProductResponse findById(Long id);
    ProductResponse create(CreateProductRequest req);
    ProductResponse update(Long id, UpdateProductRequest req);
    void delete(Long id);
}
