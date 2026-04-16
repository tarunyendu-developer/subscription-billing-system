package com.billing.system.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponseDTO {

    private Long logId;
    private Long userId;
    private String action;
    private String entity;
    private Long entityId;
    private LocalDateTime timestamp;
}