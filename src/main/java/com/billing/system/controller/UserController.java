package com.billing.system.controller;

import com.billing.system.dto.*;
import com.billing.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponseDTO register(@RequestBody UserRequestDTO dto) {
        return userService.register(dto);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public Page<UserResponseDTO> getAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String search) {
        return userService.getAll(page, size, search);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id,
                                  @RequestBody UserRequestDTO dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}