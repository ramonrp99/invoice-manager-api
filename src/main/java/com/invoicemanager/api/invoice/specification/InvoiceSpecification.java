package com.invoicemanager.api.invoice.specification;

import com.invoicemanager.api.invoice.model.InvoiceFilter;
import com.invoicemanager.api.invoice.entity.Invoice;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InvoiceSpecification {

    public static Specification<Invoice> getSpecification(InvoiceFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.customer() != null) {
                String pattern = "%" + filter.customer().toLowerCase(Locale.ROOT) + "%";

                Expression<String> fullName = criteriaBuilder.concat(
                        criteriaBuilder.concat(
                                criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("customer").get("firstName"), "")),
                                " "
                        ),
                        criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("customer").get("lastName"), ""))
                );

                predicates.add(
                        criteriaBuilder.or(
                                criteriaBuilder.like(
                                        fullName,
                                        pattern
                                ),
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("customer").get("nif"), "")),
                                        pattern
                                )
                        )
                );
            }

            if(filter.status() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filter.status()));
            }

            if(filter.issuedFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("issueDate"), filter.issuedFrom()));
            }

            if(filter.issuedTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("issueDate"), filter.issuedTo()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
