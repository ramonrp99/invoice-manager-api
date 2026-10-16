package com.invoicemanager.api.product.controller;

import com.invoicemanager.api.product.dto.CreateProductRequest;
import com.invoicemanager.api.product.dto.ProductResponse;
import com.invoicemanager.api.product.dto.UpdateProductRequest;
import com.invoicemanager.api.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody @Valid CreateProductRequest req){
        return productService.create(req);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody @Valid UpdateProductRequest req){
        return productService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.delete(id);
    }
}
