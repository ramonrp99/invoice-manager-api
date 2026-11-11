package com.invoicemanager.api.invoice.repository;

import com.invoicemanager.api.invoice.entity.Invoice;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {

    @EntityGraph(attributePaths = {"customer", "invoiceLines", "invoiceLines.product"})
    @NonNull Page<Invoice> findAll(@NonNull Specification<Invoice> specification, @NonNull Pageable pageable);

    @EntityGraph(attributePaths = {"customer", "invoiceLines", "invoiceLines.product"})
    @NonNull Optional<Invoice> findById(@NonNull Long id);
}
