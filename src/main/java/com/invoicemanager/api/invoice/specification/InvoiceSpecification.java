package com.invoicemanager.api.invoice.specification;

import com.invoicemanager.api.invoice.enums.InvoiceStatus;
import com.invoicemanager.api.invoice.entity.Invoice;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Locale;

public class InvoiceSpecification {

    public static Specification<Invoice> hasCustomerId(Long customerId) {
        return (root, query, criteriaBuilder) -> {
            if(customerId == null) return null;

            return criteriaBuilder.equal(root.get("customer").get("id"), customerId);
        };
    }

    public static Specification<Invoice> containsCustomer(String customer) {
        return (root, query, criteriaBuilder) -> {
            if(customer == null) return null;

            String pattern = "%" + customer.toLowerCase(Locale.ROOT) + "%";

            Expression<String> fullName = criteriaBuilder.concat(
                    criteriaBuilder.concat(
                            criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("customer").get("firstName"), "")),
                            " "
                    ),
                    criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("customer").get("lastName"), ""))
            );

            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            fullName,
                            pattern
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("customer").get("nif"), "")),
                            pattern
                    )
            );
        };
    }

    public static Specification<Invoice> hasStatus(InvoiceStatus status) {
        return (root, query, criteriaBuilder) -> {
            if(status == null) return null;

            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Invoice> afterOrEqualIssueDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if(date == null) return null;

            return criteriaBuilder.greaterThanOrEqualTo(root.get("issueDate"), date);
        };
    }

    public static Specification<Invoice> beforeOrEqualIssueDate(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            if(date == null) return null;

            return criteriaBuilder.lessThanOrEqualTo(root.get("issueDate"), date);
        };
    }
}
