package com.invoicemanager.api.product.service;

import com.invoicemanager.api.product.dto.CreateProductRequest;
import com.invoicemanager.api.product.dto.ProductResponse;
import com.invoicemanager.api.product.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {

    List<ProductResponse> findAll();
    ProductResponse findById(Long id);
    ProductResponse create(CreateProductRequest req);
    ProductResponse update(Long id, UpdateProductRequest req);
    void delete(Long id);
}
