package com.invoicemanager.api.product.service.impl;

import com.invoicemanager.api.product.dto.CreateProductRequest;
import com.invoicemanager.api.product.dto.ProductResponse;
import com.invoicemanager.api.product.dto.SearchProductRequest;
import com.invoicemanager.api.product.dto.UpdateProductRequest;
import com.invoicemanager.api.product.entity.Product;
import com.invoicemanager.api.product.mapper.ProductMapper;
import com.invoicemanager.api.product.mapper.ProductSearchMapper;
import com.invoicemanager.api.product.model.ProductFilter;
import com.invoicemanager.api.product.repository.ProductRepository;
import com.invoicemanager.api.product.service.ProductService;
import com.invoicemanager.api.product.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private final ProductSearchMapper productSearchMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductSearchMapper productSearchMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productSearchMapper = productSearchMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(SearchProductRequest req) {
        ProductFilter filter = productSearchMapper.toFilter(req);
        Pageable pageable = productSearchMapper.toPageable(req);
        Specification<Product> specification = ProductSpecification.getSpecification(filter);

        Page<Product> products = productRepository.findAll(specification, pageable);

        return products.map(productMapper::toResponse);
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
