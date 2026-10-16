package com.invoicemanager.api.product.mapper;

import com.invoicemanager.api.product.dto.CreateProductRequest;
import com.invoicemanager.api.product.dto.ProductResponse;
import com.invoicemanager.api.product.dto.UpdateProductRequest;
import com.invoicemanager.api.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getTaxRate(),
                product.getCreatedAt()
        );
    }

    public Product toEntity(CreateProductRequest req){
        Product product = new Product();

        product.setName(req.name());
        product.setPrice(req.price());
        product.setTaxRate(req.taxRate());

        return product;
    }

    public void updateEntity(Product product, UpdateProductRequest req){
        if(req.name() != null) product.setName(req.name());
        if(req.price() != null) product.setPrice(req.price());
        if(req.taxRate() != null) product.setTaxRate(req.taxRate());
    }
}
