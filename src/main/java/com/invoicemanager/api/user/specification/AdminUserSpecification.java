package com.invoicemanager.api.user.specification;

import com.invoicemanager.api.user.entity.User;
import com.invoicemanager.api.user.model.AdminUserFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdminUserSpecification {

    public static Specification<User> getSpecification(AdminUserFilter filter) {
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

            if(filter.email() != null) {
                String pattern = "%" + filter.email().toLowerCase(Locale.ROOT) + "%";

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("email"), "")),
                                pattern
                        )
                );
            }

            if(filter.role() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("role"), filter.role())
                );
            }

            if(filter.isActive() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("isActive"), filter.isActive())
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
