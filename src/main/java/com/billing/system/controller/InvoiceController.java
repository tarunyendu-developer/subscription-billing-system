package com.billing.system.controller;

import com.billing.system.dto.*;
import com.billing.system.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/{id}")
    public InvoiceResponseDTO getById(@PathVariable Long id) {
        return invoiceService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public Page<InvoiceResponseDTO> getByUserId(
            @PathVariable Long userId,
            @RequestParam int page,
            @RequestParam int size) {
        return invoiceService.getByUserId(userId, page, size);
    }

    @GetMapping
    public Page<InvoiceResponseDTO> getAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String status) {
        return invoiceService.getAll(page, size, status);
    }

    @PutMapping("/{id}/status")
    public InvoiceResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        return invoiceService.updateStatus(id, status);
    }
}