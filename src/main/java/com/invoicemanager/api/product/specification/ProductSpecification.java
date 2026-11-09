package com.invoicemanager.api.product.specification;

import com.invoicemanager.api.product.entity.Product;
import com.invoicemanager.api.product.model.ProductFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductSpecification {

    public static Specification<Product> getSpecification(ProductFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.name() != null) {
                String pattern = "%" + filter.name().toLowerCase(Locale.ROOT) + "%";

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("name"), "")),
                                pattern
                        )
                );
            }

            if(filter.unitPriceFrom() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("unitPrice"), filter.unitPriceFrom())
                );
            }

            if(filter.unitPriceTo() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(root.get("unitPrice"), filter.unitPriceTo())
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
