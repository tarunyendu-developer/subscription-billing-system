package com.billing.system.controller;

import com.billing.system.dto.*;
import com.billing.system.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public List<AuditLogResponseDTO> getAll() {
        return auditLogService.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<AuditLogResponseDTO> getByUserId(@PathVariable Long userId) {
        return auditLogService.getByUserId(userId);
    }
}