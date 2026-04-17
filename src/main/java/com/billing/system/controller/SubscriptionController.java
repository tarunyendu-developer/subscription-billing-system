package com.billing.system.controller;

import com.billing.system.dto.*;
import com.billing.system.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public SubscriptionResponseDTO create(@RequestBody SubscriptionRequestDTO dto) {
        return subscriptionService.create(dto);
    }

    @GetMapping("/{id}")
    public SubscriptionResponseDTO getById(@PathVariable Long id) {
        return subscriptionService.getById(id);
    }

    @PutMapping("/cancel/{id}")
    public void cancel(@PathVariable Long id) {
        subscriptionService.cancel(id);
    }
    @GetMapping
    public Page<SubscriptionResponseDTO> getAll(@RequestParam int page, @RequestParam int size, @RequestParam String status) {
        return subscriptionService.getAll(page, size, status);
    }

    @GetMapping("/user/{userId}")
    public Page<SubscriptionResponseDTO> getByUserId(@PathVariable Long userId, @RequestParam int page, @RequestParam int size) {
        return subscriptionService.getByUserId(userId, page, size);
    }

    @PutMapping("/upgrade/{subscriptionId}")
    public SubscriptionResponseDTO upgrade(@PathVariable Long subscriptionId, @RequestParam Long newPlanId) {
        return subscriptionService.upgrade(subscriptionId, newPlanId);
    }
}