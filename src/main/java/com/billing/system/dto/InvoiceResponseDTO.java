package com.billing.system.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponseDTO {

    private Long invoiceId;
    private Long subscriptionId;
    private Long userId;
    private Double amount;
    private LocalDateTime dueDate;
    private LocalDateTime paidDate;
    private String status;
}