package com.billing.system.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {

    private long totalUsers;
    private long totalSubscriptions;
    private long activeSubscriptions;
    private double totalRevenue;
}