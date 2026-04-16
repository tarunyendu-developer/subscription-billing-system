package com.billing.system.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {

    private Long invoiceId;
    private Long userId;
    private Double amount;
    private String paymentMethod;
}