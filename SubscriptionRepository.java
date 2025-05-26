package com.example.demo.repository;

import com.example.demo.model.SubscriptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionRequest, Long> {
}