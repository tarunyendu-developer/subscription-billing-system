package com.billing.system.service;

import com.billing.system.dto.*;

public interface SubscriptionService {

    SubscriptionResponseDTO create(SubscriptionRequestDTO dto);

    SubscriptionResponseDTO getById(Long id);

    void cancel(Long id);
}