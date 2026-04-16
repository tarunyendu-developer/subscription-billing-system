package com.billing.system.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanResponseDTO {

    private Long planId;
    private String planName;
    private String description;
    private Double price;
    private String billingCycle;
    private String status;
    private LocalDateTime createdAt;
}