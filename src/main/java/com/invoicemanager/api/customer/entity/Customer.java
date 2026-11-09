package com.invoicemanager.api.customer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "customers")
@Getter @Setter
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotNull
    @Size(max = 100)
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotNull
    @Size(max = 9)
    @Column(name = "nif", nullable = false, length = 9)
    private String nif;

    @NotNull
    @Size(max = 100)
    @Column(name = "street", nullable = false, length = 100)
    private String street;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @Size(max = 3)
    @Column(name = "number_letter", length = 3)
    private String numberLetter;

    @NotNull
    @Size(max = 5)
    @Column(name = "postal_code", nullable = false, length = 5)
    private String postalCode;

    @NotNull
    @Size(max = 100)
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    private void onCreate() {
        if(createdAt == null) createdAt = Instant.now();
    }
}
