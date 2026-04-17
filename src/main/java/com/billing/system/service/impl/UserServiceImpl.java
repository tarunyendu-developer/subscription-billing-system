package com.billing.system.service.impl;

import com.billing.system.dto.*;
import com.billing.system.entity.*;
import com.billing.system.exception.ResourceNotFoundException;
import com.billing.system.repository.UserRepository;
import com.billing.system.service.AuditLogService;
import com.billing.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @Override
    public UserResponseDTO register(UserRequestDTO dto) {

        log.info("Registering user with email: {}", dto.getEmail());

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.valueOf(dto.getRole()))
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        log.info("User registered successfully with ID: {}", user.getUserId());
        auditLogService.log(user.getUserId(), "CREATE", "USER", user.getUserId());

        return mapToResponse(user);
    }

    @Override
    public UserResponseDTO getById(Long id) {

        log.info("Fetching user by ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new ResourceNotFoundException("User not found");
                });

        return mapToResponse(user);
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO dto) {

        log.info("Updating user with ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found for update with ID: {}", id);
                    return new ResourceNotFoundException("User not found");
                });

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        log.info("User updated successfully with ID: {}", id);

        return mapToResponse(user);
    }

    @Override
    public void delete(Long id) {

        log.info("Deleting user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            log.error("User not found for delete with ID: {}", id);
            throw new ResourceNotFoundException("User not found");
        }

        userRepository.deleteById(id);

        log.info("User deleted successfully with ID: {}", id);
    }

    private UserResponseDTO mapToResponse(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public Page<UserResponseDTO> getAll(int page, int size, String search) {

        log.info("Fetching users with search: {}", search);

        Pageable pageable = PageRequest.of(page, size);

        Page<User> users;

        if (search != null && !search.isEmpty()) {
            users = userRepository.findByUsernameContainingIgnoreCase(search, pageable);
        } else {
            users = userRepository.findAll(pageable);
        }

        return users.map(this::mapToResponse);
    }
}