package com.billing.system.service.impl;

import com.billing.system.dto.*;
import com.billing.system.entity.*;
import com.billing.system.exception.ResourceNotFoundException;
import com.billing.system.repository.PlanRepository;
import com.billing.system.service.AuditLogService;
import com.billing.system.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private static final Logger log = LoggerFactory.getLogger(PlanServiceImpl.class);

    private final PlanRepository planRepository;
    private final AuditLogService auditLogService;

    @Override
    public PlanResponseDTO create(PlanRequestDTO dto) {

        log.info("Creating plan: {}", dto.getPlanName());

        Plan plan = Plan.builder()
                .planName(dto.getPlanName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .billingCycle(BillingCycle.valueOf(dto.getBillingCycle()))
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        planRepository.save(plan);

        log.info("Plan created successfully with ID: {}", plan.getPlanId());

        return mapToResponse(plan);
    }

    @Override
    public List<PlanResponseDTO> getAll() {

        log.info("Fetching all plans");

        return planRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PlanResponseDTO getById(Long id) {

        log.info("Fetching plan by ID: {}", id);

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Plan not found with ID: {}", id);
                    return new ResourceNotFoundException("Plan not found");
                });

        return mapToResponse(plan);
    }

    @Override
    public PlanResponseDTO update(Long id, PlanRequestDTO dto) {

        log.info("Updating plan with ID: {}", id);

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Plan not found for update with ID: {}", id);
                    return new ResourceNotFoundException("Plan not found");
                });

        plan.setPlanName(dto.getPlanName());
        plan.setDescription(dto.getDescription());
        plan.setPrice(dto.getPrice());
        plan.setBillingCycle(BillingCycle.valueOf(dto.getBillingCycle()));

        planRepository.save(plan);

        log.info("Plan updated successfully with ID: {}", id);

        return mapToResponse(plan);
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting plan with ID: {}", id);

        if (!planRepository.existsById(id)) {
            log.error("Plan not found for delete with ID: {}", id);
            throw new ResourceNotFoundException("Plan not found");
        }

        planRepository.deleteById(id);

        log.info("Plan deleted successfully with ID: {}", id);
    }

    private PlanResponseDTO mapToResponse(Plan plan) {
        return PlanResponseDTO.builder()
                .planId(plan.getPlanId())
                .planName(plan.getPlanName())
                .description(plan.getDescription())
                .price(plan.getPrice())
                .billingCycle(plan.getBillingCycle().name())
                .status(plan.getStatus())
                .createdAt(plan.getCreatedAt())
                .build();
    }
}