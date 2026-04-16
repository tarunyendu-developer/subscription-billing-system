package com.billing.system.service;

import com.billing.system.dto.*;

import java.util.List;

public interface PaymentService {

    PaymentResponseDTO create(PaymentRequestDTO dto);

    PaymentResponseDTO getById(Long id);

    List<PaymentResponseDTO> getByUserId(Long userId);

    List<PaymentResponseDTO> getByInvoiceId(Long invoiceId);
}