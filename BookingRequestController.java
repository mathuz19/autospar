package com.example.demo.controller; // Updated package

import com.example.demo.model.BookingRequest; // Updated import
import com.example.demo.repository.BookingRequestRepository; // Updated import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingRequestController {

    @Autowired
    private BookingRequestRepository bookingRepository;

    @PostMapping
    public ResponseEntity<String> submitBooking(
            @Validated @RequestBody BookingRequest request) {
        // Save to database
        BookingRequest booking = new BookingRequest();
        booking.setCarPark(request.getCarPark());
        booking.setParkingName(request.getParkingName());
        booking.setParkingNumber(request.getParkingNumber());
        bookingRepository.save(booking);

        String responseMessage = String.format(
                "Booking request received for %s at %s. We will contact you at %s.",
                request.getParkingName(), request.getCarPark(), request.getParkingNumber());

        return ResponseEntity.ok(responseMessage);
    }
}