package com.billing.system.service;

import com.billing.system.dto.*;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponseDTO register(UserRequestDTO dto);

    UserResponseDTO getById(Long id);

    Page<UserResponseDTO> getAll(int page, int size, String search);

    UserResponseDTO update(Long id, UserRequestDTO dto);

    void delete(Long id);
}