package com.realestate.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Column(nullable = false)
    private BigDecimal bookingAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(updatable = false)
    private LocalDateTime bookingDate;

    @PrePersist
    public void onCreate() {
        this.bookingDate = LocalDateTime.now();
        if (this.status == null) this.status = BookingStatus.PENDING;
    }
}
