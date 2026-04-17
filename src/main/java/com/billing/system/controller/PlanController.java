package com.billing.system.controller;

import com.billing.system.dto.*;
import com.billing.system.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public PlanResponseDTO create(@RequestBody PlanRequestDTO dto) {
        return planService.create(dto);
    }

    @GetMapping
    public List<PlanResponseDTO> getAll() {
        return planService.getAll();
    }

    @GetMapping("/{id}")
    public PlanResponseDTO getById(@PathVariable Long id) {
        return planService.getById(id);
    }

    @PutMapping("/{id}")
    public PlanResponseDTO update(@PathVariable Long id,
                                  @RequestBody PlanRequestDTO dto) {
        return planService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        planService.delete(id);
    }
}