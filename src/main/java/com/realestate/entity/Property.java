package com.realestate.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    private PropertyType type; // RESIDENTIAL, COMMERCIAL, LAND

    @Enumerated(EnumType.STRING)
    private PropertyPurpose purpose; // SALE, RENT

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String city;

    private Integer bedrooms;
    private Integer bathrooms;
    private Double areaSqft;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status; // AVAILABLE, BOOKED, SOLD, RENTED

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private User agent;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) this.status = PropertyStatus.AVAILABLE;
    }
}
