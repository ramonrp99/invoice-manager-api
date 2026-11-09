package com.invoicemanager.api.customer.specification;

import com.invoicemanager.api.customer.entity.Customer;
import com.invoicemanager.api.customer.model.CustomerFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerSpecification {

    public static Specification<Customer> getSpecification(CustomerFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.name() != null) {
                String pattern = "%" + filter.name().toLowerCase(Locale.ROOT) + "%";

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.concat(
                                        criteriaBuilder.concat(
                                                criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("firstName"), "")),
                                                " "
                                        ),
                                        criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("lastName"), ""))
                                ),
                                pattern
                        )
                );
            }

            if(filter.nif() != null) {
                String pattern = "%" + filter.nif().toLowerCase(Locale.ROOT) + "%";

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("nif"), "")),
                                pattern
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
