package com.billing.system.controller;

import com.billing.system.dto.*;
import com.billing.system.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponseDTO create(@RequestBody PaymentRequestDTO dto) {
        return paymentService.create(dto);
    }

    @GetMapping("/{id}")
    public PaymentResponseDTO getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<PaymentResponseDTO> getByUserId(@PathVariable Long userId) {
        return paymentService.getByUserId(userId);
    }

    @GetMapping("/invoice/{invoiceId}")
    public List<PaymentResponseDTO> getByInvoiceId(@PathVariable Long invoiceId) {
        return paymentService.getByInvoiceId(invoiceId);
    }
}