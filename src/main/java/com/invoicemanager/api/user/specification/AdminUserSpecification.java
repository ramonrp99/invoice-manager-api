package com.invoicemanager.api.user.specification;

import com.invoicemanager.api.user.entity.User;
import com.invoicemanager.api.user.enums.UserRole;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class AdminUserSpecification {

    public static Specification<User> containsName(String name) {
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

    public static Specification<User> containsEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if(email == null) return null;

            String pattern = "%" + email.toLowerCase(Locale.ROOT) + "%";

            return criteriaBuilder.like(
                    criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("email"), "")),
                    pattern
            );
        };
    }

    public static Specification<User> hasRole(UserRole role) {
        return (root, query, criteriaBuilder) -> {
            if(role == null) return null;

            return criteriaBuilder.equal(root.get("role"), role);
        };
    }

    public static Specification<User> hasActive(Boolean active) {
        return (root, query, criteriaBuilder) -> {
            if(active == null) return null;

            return criteriaBuilder.equal(root.get("isActive"), active);
        };
    }
}
