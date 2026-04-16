package com.billing.system.service.impl;

import com.billing.system.dto.*;
import com.billing.system.entity.*;
import com.billing.system.repository.*;
import com.billing.system.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public SubscriptionResponseDTO create(SubscriptionRequestDTO dto) {

        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = plan.getBillingCycle() == BillingCycle.MONTHLY
                ? start.plusMonths(1)
                : start.plusYears(1);

        Subscription subscription = Subscription.builder()
                .userId(dto.getUserId())
                .planId(dto.getPlanId())
                .startDate(start)
                .endDate(end)
                .status(SubscriptionStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        subscriptionRepository.save(subscription);

        // 🔥 Auto Invoice Creation
        Invoice invoice = Invoice.builder()
                .subscriptionId(subscription.getSubscriptionId())
                .userId(dto.getUserId())
                .amount(plan.getPrice())
                .dueDate(start.plusDays(7))
                .status(InvoiceStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        invoiceRepository.save(invoice);

        return mapToResponse(subscription);
    }

    @Override
    public SubscriptionResponseDTO getById(Long id) {

        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        return mapToResponse(s);
    }

    @Override
    public void cancel(Long id) {

        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        s.setStatus(SubscriptionStatus.CANCELLED);
        subscriptionRepository.save(s);
    }

    private SubscriptionResponseDTO mapToResponse(Subscription s) {
        return SubscriptionResponseDTO.builder()
                .subscriptionId(s.getSubscriptionId())
                .userId(s.getUserId())
                .planId(s.getPlanId())
                .startDate(s.getStartDate())
                .endDate(s.getEndDate())
                .status(s.getStatus().name())
                .build();
    }
}