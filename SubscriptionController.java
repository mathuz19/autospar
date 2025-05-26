package com.example.demo.controller;

import com.example.demo.model.SubscriptionRequest;
import com.example.demo.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribe")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @PostMapping
    public ResponseEntity<String> subscribe(
            @Validated @RequestBody SubscriptionRequest request) {
        // Save the subscription to the database
        SubscriptionRequest subscription = new SubscriptionRequest();
        subscription.setEmail(request.getEmail());
        subscriptionRepository.save(subscription);

        String responseMessage = String.format(
                "Thank you, %s! You are now subscribed to TrustySpot updates.",
                request.getEmail());

        return ResponseEntity.ok(responseMessage);
    }
}