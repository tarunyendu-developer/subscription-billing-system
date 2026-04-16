package com.billing.system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    private String planName;

    private String description;

    private Double price;

    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;

    private String status;

    private LocalDateTime createdAt;
}