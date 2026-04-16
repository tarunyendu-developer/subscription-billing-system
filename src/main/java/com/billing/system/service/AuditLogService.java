package com.billing.system.service;

import com.billing.system.dto.*;

import java.util.List;

public interface AuditLogService {

    void log(Long userId, String action, String entity, Long entityId);

    List<AuditLogResponseDTO> getAll();

    List<AuditLogResponseDTO> getByUserId(Long userId);
}