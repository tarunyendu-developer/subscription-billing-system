package com.billing.system.service.impl;

import com.billing.system.dto.*;
import com.billing.system.entity.*;
import com.billing.system.repository.PlanRepository;
import com.billing.system.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Override
    public PlanResponseDTO create(PlanRequestDTO dto) {

        Plan plan = Plan.builder()
                .planName(dto.getPlanName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .billingCycle(BillingCycle.valueOf(dto.getBillingCycle()))
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        planRepository.save(plan);

        return mapToResponse(plan);
    }

    @Override
    public List<PlanResponseDTO> getAll() {
        return planRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PlanResponseDTO getById(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        return mapToResponse(plan);
    }

    @Override
    public PlanResponseDTO update(Long id, PlanRequestDTO dto) {

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        plan.setPlanName(dto.getPlanName());
        plan.setDescription(dto.getDescription());
        plan.setPrice(dto.getPrice());
        plan.setBillingCycle(BillingCycle.valueOf(dto.getBillingCycle()));

        planRepository.save(plan);

        return mapToResponse(plan);
    }

    @Override
    public void delete(Long id) {
        planRepository.deleteById(id);
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