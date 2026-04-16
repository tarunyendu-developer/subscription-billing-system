package com.billing.system.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponseDTO {

    private Long subscriptionId;
    private Long userId;
    private Long planId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}