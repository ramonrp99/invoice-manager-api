package com.invoicemanager.api.invoice.entity;

import com.invoicemanager.api.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "invoice_lines")
@Getter @Setter
public class InvoiceLine {

    @Id @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Size(max = 100)
    @Column(name = "name_snapshot", length = 100)
    private String nameSnapshot;

    @NotNull
    @Column(name = "quantity", nullable = false, precision = 10, scale = 3)
    private BigDecimal quantity;

    @Column(name = "unit_price_snapshot", precision = 10, scale = 2)
    private BigDecimal unitPriceSnapshot;

    @Column(name = "line_subtotal_snapshot", precision = 10, scale = 2)
    private BigDecimal lineSubtotalSnapshot;

    @Column(name = "tax_rate_snapshot", precision = 5, scale = 2)
    private BigDecimal taxRateSnapshot;

    @Column(name = "tax_amount_snapshot", precision = 10, scale = 2)
    private BigDecimal taxAmountSnapshot;

    @Column(name = "line_total_snapshot", precision = 10, scale = 2)
    private BigDecimal lineTotalSnapshot;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    private void onCreate() {
        if(createdAt == null) createdAt = Instant.now();
    }
}
