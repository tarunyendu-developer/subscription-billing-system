package com.billing.system.controller;

import com.billing.system.dto.DashboardResponseDTO;
import com.billing.system.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardResponseDTO getSummary() {
        return dashboardService.getSummary();
    }
}