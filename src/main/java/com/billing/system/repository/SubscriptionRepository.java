package com.billing.system.repository;

import com.billing.system.entity.Subscription;
import com.billing.system.entity.SubscriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Page<Subscription> findByStatus(SubscriptionStatus status, Pageable pageable);

    Page<Subscription> findByUserId(Long userId, Pageable pageable);
}