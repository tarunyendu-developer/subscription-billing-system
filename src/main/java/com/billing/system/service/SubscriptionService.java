package com.billing.system.service;

import com.billing.system.dto.*;
import org.springframework.data.domain.Page;

public interface SubscriptionService {

    SubscriptionResponseDTO create(SubscriptionRequestDTO dto);

    SubscriptionResponseDTO getById(Long id);

    void cancel(Long id);

    Page<SubscriptionResponseDTO> getAll(int page, int size, String status);

    Page<SubscriptionResponseDTO> getByUserId(Long userId, int page, int size);

    SubscriptionResponseDTO upgrade(Long subscriptionId, Long newPlanId);
}