package com.invoicemanager.api.invoice.repository;

import com.invoicemanager.api.invoice.entity.Invoice;
import lombok.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @EntityGraph(attributePaths = {"invoiceLines", "invoiceLines.product"})
    @NonNull List<Invoice> findAll();

    @EntityGraph(attributePaths = {"invoiceLines", "invoiceLines.product"})
    @NonNull Optional<Invoice> findById(@NonNull Long id);
}
