package com.billing.system.service.impl;

import com.billing.system.dto.*;
import com.billing.system.entity.*;
import com.billing.system.repository.InvoiceRepository;
import com.billing.system.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Override
    public InvoiceResponseDTO getById(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        return mapToResponse(invoice);
    }

    @Override
    public Page<InvoiceResponseDTO> getByUserId(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return invoiceRepository.findByUserId(userId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<InvoiceResponseDTO> getAll(int page, int size, String status) {

        Pageable pageable = PageRequest.of(page, size);

        return invoiceRepository.findByStatus(
                        InvoiceStatus.valueOf(status), pageable)
                .map(this::mapToResponse);
    }

    @Override
    public InvoiceResponseDTO updateStatus(Long id, String status) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoice.setStatus(InvoiceStatus.valueOf(status));
        invoiceRepository.save(invoice);

        return mapToResponse(invoice);
    }

    private InvoiceResponseDTO mapToResponse(Invoice i) {
        return InvoiceResponseDTO.builder()
                .invoiceId(i.getInvoiceId())
                .subscriptionId(i.getSubscriptionId())
                .userId(i.getUserId())
                .amount(i.getAmount())
                .dueDate(i.getDueDate())
                .paidDate(i.getPaidDate())
                .status(i.getStatus().name())
                .build();
    }
}