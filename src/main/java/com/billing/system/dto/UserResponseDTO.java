package com.billing.system.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long userId;
    private String username;
    private String email;
    private String role;
    private String status;
    private LocalDateTime createdAt;
}