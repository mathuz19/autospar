package com.example.demo.service; // Updated package

import com.example.demo.model.BookingRequest; // Updated import
import com.example.demo.repository.BookingRequestRepository; // Updated import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingRequestService {

    @Autowired
    private BookingRequestRepository repository;

    public void saveBooking(BookingRequest request) {
        repository.save(request);
    }
}