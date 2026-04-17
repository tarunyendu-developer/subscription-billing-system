package com.billing.system.service.impl;

import com.billing.system.dto.*;
import com.billing.system.entity.*;
import com.billing.system.exception.ResourceNotFoundException;
import com.billing.system.repository.*;
import com.billing.system.service.AuditLogService;
import com.billing.system.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final AuditLogService auditLogService;

    @Override
    public PaymentResponseDTO create(PaymentRequestDTO dto) {

        log.info("Processing payment for invoiceId: {}", dto.getInvoiceId());

        Invoice invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() -> {
                    log.error("Invoice not found with ID: {}", dto.getInvoiceId());
                    return new ResourceNotFoundException("Invoice not found");
                });

        Payment payment = Payment.builder()
                .invoiceId(dto.getInvoiceId())
                .userId(dto.getUserId())
                .amount(dto.getAmount())
                .paymentMethod(dto.getPaymentMethod())
                .paymentDate(LocalDateTime.now())
                .status(PaymentStatus.SUCCESS)
                .build();

        paymentRepository.save(payment);

        // Update invoice
        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaidDate(LocalDateTime.now());
        invoiceRepository.save(invoice);

        log.info("Payment successful and invoice updated for invoiceId: {}", dto.getInvoiceId());
        auditLogService.log(dto.getUserId(), "PAYMENT", "INVOICE", dto.getInvoiceId());

        return mapToResponse(payment);
    }

    @Override
    public PaymentResponseDTO getById(Long id) {

        log.info("Fetching payment by ID: {}", id);

        Payment p = paymentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Payment not found with ID: {}", id);
                    return new ResourceNotFoundException("Payment not found");
                });

        return mapToResponse(p);
    }

    @Override
    public List<PaymentResponseDTO> getByUserId(Long userId) {

        log.info("Fetching payments for userId: {}", userId);

        return paymentRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<PaymentResponseDTO> getByInvoiceId(Long invoiceId) {

        log.info("Fetching payments for invoiceId: {}", invoiceId);

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