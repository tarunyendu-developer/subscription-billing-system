package com.billing.system.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {

    private Long paymentId;
    private Long invoiceId;
    private Long userId;
    private Double amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String status;
}