package com.invoicemanager.api.product.service.impl;

import com.invoicemanager.api.product.dto.CreateProductRequest;
import com.invoicemanager.api.product.dto.ProductResponse;
import com.invoicemanager.api.product.dto.UpdateProductRequest;
import com.invoicemanager.api.product.entity.Product;
import com.invoicemanager.api.product.mapper.ProductMapper;
import com.invoicemanager.api.product.repository.ProductRepository;
import com.invoicemanager.api.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(productMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return productMapper.toResponse(findProduct(id));
    }

    @Override
    public ProductResponse create(CreateProductRequest req) {
        validateUniqueName(req.name());

        Product product = productRepository.save(productMapper.toEntity(req));

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse update(Long id, UpdateProductRequest req) {
        Product product = findProduct(id);

        validateUniqueName(req.name(), id);
        productMapper.updateEntity(product, req);

        Product updated = productRepository.save(product);

        return productMapper.toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
        }

        productRepository.deleteById(id);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado."));
    }

    private void validateUniqueName(String name) {
        if(name != null && productRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un producto con ese nombre");
        }
    }

    private void validateUniqueName(String name, Long id) {
        if(name != null && productRepository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un producto con ese nombre");
        }
    }
}
