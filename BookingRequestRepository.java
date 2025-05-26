package com.example.demo.repository; // Updated package

import com.example.demo.model.BookingRequest; // Updated import
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {
}