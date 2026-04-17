package com.billing.system.service.impl;

import com.billing.system.dto.DashboardResponseDTO;
import com.billing.system.entity.*;
import com.billing.system.repository.*;
import com.billing.system.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardServiceImpl.class);

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public DashboardResponseDTO getSummary() {

        log.info("Fetching dashboard summary");

        long totalUsers = userRepository.count();
        long totalSubscriptions = subscriptionRepository.count();

        long activeSubscriptions = subscriptionRepository.findAll()
                .stream()
                .filter(s -> s.getStatus() == SubscriptionStatus.ACTIVE)
                .count();

        double totalRevenue = paymentRepository.findAll()
                .stream()
                .filter(p -> p.getStatus() == PaymentStatus.SUCCESS)
                .mapToDouble(Payment::getAmount)
                .sum();

        log.info("Dashboard data calculated successfully");

        return DashboardResponseDTO.builder()
                .totalUsers(totalUsers)
                .totalSubscriptions(totalSubscriptions)
                .activeSubscriptions(activeSubscriptions)
                .totalRevenue(totalRevenue)
                .build();
    }
}