package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingRequestController {

    @Autowired
    private BookingRequestRepository repository;

    @PostMapping
    public ResponseEntity<String> createBookingRequest(@RequestBody BookingRequest bookingRequest) {
        repository.save(bookingRequest);
        return ResponseEntity.ok("Booking request submitted successfully");
    }
}