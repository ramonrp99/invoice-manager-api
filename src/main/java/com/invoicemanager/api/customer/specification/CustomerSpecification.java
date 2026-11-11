package com.invoicemanager.api.customer.specification;

import com.invoicemanager.api.customer.entity.Customer;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class CustomerSpecification {

    public static Specification<Customer> containsName(String name) {
        return (root, query, criteriaBuilder) -> {
            if(name == null) return null;

            String pattern = "%" + name.toLowerCase(Locale.ROOT) + "%";

            Expression<String> fullName = criteriaBuilder.concat(
                    criteriaBuilder.concat(
                            criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("firstName"), "")),
                            " "
                    ),
                    criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("lastName"), ""))
            );

            return criteriaBuilder.like(
                    fullName,
                    pattern
            );
        };
    }

    public static Specification<Customer> containsNif(String nif) {
        return (root, query, criteriaBuilder) -> {
            if(nif == null) return null;

            String pattern = "%" + nif.toLowerCase(Locale.ROOT) + "%";

            return criteriaBuilder.like(
                    criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("nif"), "")),
                    pattern
            );
        };
    }
}
