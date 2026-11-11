package com.invoicemanager.api.product.specification;

import com.invoicemanager.api.product.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Locale;

public class ProductSpecification {

    public static Specification<Product> containsName(String name) {
        return (root, query, criteriaBuilder) -> {
            if(name == null) return null;

            String pattern = "%" + name.toLowerCase(Locale.ROOT) + "%";

            return criteriaBuilder.like(
                    criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("name"), "")),
                    pattern
            );
        };
    }

    public static Specification<Product> greaterOrEqualUnitPrice(BigDecimal number) {
        return (root, query, criteriaBuilder) -> {
            if(number == null) return null;

            return criteriaBuilder.greaterThanOrEqualTo(root.get("unitPrice"), number);
        };
    }

    public static Specification<Product> lessOrEqualUnitPrice(BigDecimal number) {
        return (root, query, criteriaBuilder) -> {
            if(number == null) return null;

            return criteriaBuilder.lessThanOrEqualTo(root.get("unitPrice"), number);
        };
    }
}
