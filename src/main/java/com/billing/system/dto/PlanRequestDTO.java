package com.billing.system.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanRequestDTO {

    private String planName;
    private String description;
    private Double price;
    private String billingCycle;
}