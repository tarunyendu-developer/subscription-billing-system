package com.billing.system.service.impl;

import com.billing.system.dto.*;
import com.billing.system.entity.*;
import com.billing.system.exception.ResourceNotFoundException;
import com.billing.system.repository.*;
import com.billing.system.service.AuditLogService;
import com.billing.system.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final InvoiceRepository invoiceRepository;
    private final AuditLogService auditLogService;

    @Override
    public SubscriptionResponseDTO create(SubscriptionRequestDTO dto) {

        log.info("Creating subscription for userId: {} and planId: {}", dto.getUserId(), dto.getPlanId());

        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> {
                    log.error("Plan not found with ID: {}", dto.getPlanId());
                    return new ResourceNotFoundException("Plan not found");
                });

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

        log.info("Subscription created with ID: {}", subscription.getSubscriptionId());
        auditLogService.log(dto.getUserId(), "CREATE", "SUBSCRIPTION", subscription.getSubscriptionId());

        // Auto invoice
        Invoice invoice = Invoice.builder()
                .subscriptionId(subscription.getSubscriptionId())
                .userId(dto.getUserId())
                .amount(plan.getPrice())
                .dueDate(start.plusDays(7))
                .status(InvoiceStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        invoiceRepository.save(invoice);

        log.info("Invoice generated for subscriptionId: {}", subscription.getSubscriptionId());

        return mapToResponse(subscription);
    }

    @Override
    public SubscriptionResponseDTO getById(Long id) {

        log.info("Fetching subscription with ID: {}", id);

        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Subscription not found with ID: {}", id);
                    return new ResourceNotFoundException("Subscription not found");
                });

        return mapToResponse(s);
    }

    @Override
    public void cancel(Long id) {

        log.info("Cancelling subscription with ID: {}", id);

        Subscription s = subscriptionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Subscription not found with ID: {}", id);
                    return new ResourceNotFoundException("Subscription not found");
                });

        s.setStatus(SubscriptionStatus.CANCELLED);
        subscriptionRepository.save(s);

        log.info("Subscription cancelled successfully with ID: {}", id);
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

    @Override
    public Page<SubscriptionResponseDTO> getAll(int page, int size, String status) {

        log.info("Fetching subscriptions with status: {}", status);

        Pageable pageable = PageRequest.of(page, size);

        return subscriptionRepository.findByStatus(
                        SubscriptionStatus.valueOf(status), pageable)
                .map(this::mapToResponse);
    }

    @Override
    public Page<SubscriptionResponseDTO> getByUserId(Long userId, int page, int size) {

        log.info("Fetching subscriptions for userId: {}", userId);

        Pageable pageable = PageRequest.of(page, size);

        return subscriptionRepository.findByUserId(userId, pageable)
                .map(this::mapToResponse);
    }

    @Override
    public SubscriptionResponseDTO upgrade(Long subscriptionId, Long newPlanId) {

        log.info("Upgrading subscription ID: {} to new plan ID: {}", subscriptionId, newPlanId);

        Subscription oldSub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        oldSub.setStatus(SubscriptionStatus.CANCELLED);
        subscriptionRepository.save(oldSub);

        SubscriptionRequestDTO dto = new SubscriptionRequestDTO();
        dto.setUserId(oldSub.getUserId());
        dto.setPlanId(newPlanId);

        return create(dto); // reuse existing logic
    }
}