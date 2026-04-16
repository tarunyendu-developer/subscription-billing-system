package com.billing.system.repository;

import com.billing.system.entity.Invoice;
import com.billing.system.entity.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Page<Invoice> findByUserId(Long userId, Pageable pageable);

    Page<Invoice> findByStatus(InvoiceStatus status, Pageable pageable);
}