package com.billing.system.service.impl;

import com.billing.system.dto.*;
import com.billing.system.entity.AuditLog;
import com.billing.system.repository.AuditLogRepository;
import com.billing.system.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private static final Logger log = LoggerFactory.getLogger(AuditLogServiceImpl.class);
    private final AuditLogRepository auditLogRepository;


    @Override
    public void log(Long userId, String action, String entity, Long entityId) {

        log.info("Logging action: {} on entity: {} with ID: {}", action, entity, entityId);

        AuditLog logEntity = AuditLog.builder()
                .userId(userId)
                .action(action)
                .entity(entity)
                .entityId(entityId)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(logEntity);
    }

    @Override
    public List<AuditLogResponseDTO> getAll() {

        log.info("Fetching all audit logs");

        return auditLogRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AuditLogResponseDTO> getByUserId(Long userId) {

        log.info("Fetching audit logs for userId: {}", userId);

        return auditLogRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AuditLogResponseDTO mapToResponse(AuditLog log) {
        return AuditLogResponseDTO.builder()
                .logId(log.getLogId())
                .userId(log.getUserId())
                .action(log.getAction())
                .entity(log.getEntity())
                .entityId(log.getEntityId())
                .timestamp(log.getTimestamp())
                .build();
    }
}