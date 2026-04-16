package com.billing.system.service;

import com.billing.system.dto.*;

import java.util.List;

public interface PlanService {

    PlanResponseDTO create(PlanRequestDTO dto);

    List<PlanResponseDTO> getAll();

    PlanResponseDTO getById(Long id);

    PlanResponseDTO update(Long id, PlanRequestDTO dto);

    void delete(Long id);
}