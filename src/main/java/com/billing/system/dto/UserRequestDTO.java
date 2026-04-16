package com.billing.system.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    private String username;
    private String email;
    private String password;
    private String role;
}