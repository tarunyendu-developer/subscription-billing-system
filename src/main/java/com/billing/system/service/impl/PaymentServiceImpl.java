package com.billing.system.service.impl;

import com.billing.system.dto.*;
import com.billing.system.entity.*;
import com.billing.system.repository.*;
import com.billing.system.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public PaymentResponseDTO create(PaymentRequestDTO dto) {

        Invoice invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Payment payment = Payment.builder()
                .invoiceId(dto.getInvoiceId())
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .paymentMethod(dto.getPaymentMethod())
                .paymentDate(LocalDateTime.now())
                .status(PaymentStatus.SUCCESS)
                .build();

        paymentRepository.save(payment);

        // 🔥 Update Invoice
        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaidDate(LocalDateTime.now());
        invoiceRepository.save(invoice);

        return mapToResponse(payment);
    }

    @Override
    public PaymentResponseDTO getById(Long id) {
        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return mapToResponse(p);
    }

    @Override
    public List<PaymentResponseDTO> getByUserId(Long userId) {
        return paymentRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<PaymentResponseDTO> getByInvoiceId(Long invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PaymentResponseDTO mapToResponse(Payment p) {
        return PaymentResponseDTO.builder()
                .paymentId(p.getPaymentId())
                .invoiceId(p.getInvoiceId())
                .userId(p.getUserId())
                .amount(p.getAmount())
                .paymentMethod(p.getPaymentMethod())
                .paymentDate(p.getPaymentDate())
                .status(p.getStatus().name())
                .build();
    }
}