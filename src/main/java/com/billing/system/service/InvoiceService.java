package com.billing.system.service;

import com.billing.system.dto.*;
import org.springframework.data.domain.Page;

public interface InvoiceService {

    InvoiceResponseDTO getById(Long id);

    Page<InvoiceResponseDTO> getByUserId(Long userId, int page, int size);

    Page<InvoiceResponseDTO> getAll(int page, int size, String status);

    InvoiceResponseDTO updateStatus(Long id, String status);
}