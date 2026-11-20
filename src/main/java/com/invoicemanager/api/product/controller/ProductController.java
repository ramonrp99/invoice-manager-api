package com.invoicemanager.api.product.controller;

import com.invoicemanager.api.product.dto.CreateProductRequest;
import com.invoicemanager.api.product.dto.ProductResponse;
import com.invoicemanager.api.product.dto.SearchProductRequest;
import com.invoicemanager.api.product.dto.UpdateProductRequest;
import com.invoicemanager.api.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.invoicemanager.api.config.ApiConfig.API_BASE_PATH;

@RestController
@RequestMapping(API_BASE_PATH + "/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductResponse> getProducts(@Valid SearchProductRequest req) {
        return productService.findAll(req);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id){
        productService.delete(id);
    }
}
