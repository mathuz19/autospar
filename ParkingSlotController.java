package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ParkingSlot;
import com.example.demo.service.ParkingSlotService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/parking")
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @PostConstruct
    public void init() {
        // Ensure service initializes slots
        parkingSlotService.init();
    }

    @GetMapping("/slots")
    public ResponseEntity<List<ParkingSlot>> getAllSlots() {
        List<ParkingSlot> slots = parkingSlotService.getAllSlotsSortedByAvailability();
        List<ParkingSlot> availableSlots = slots.stream()
                .filter(slot -> !slot.isOccupied())
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableSlots);
    }

    @PostMapping("/allocate")
    public ResponseEntity<String> allocateSlot() {
        String result = parkingSlotService.allocateSlot();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/allocate/{slotId}")
    public ResponseEntity<String> allocateSlot(@PathVariable Long slotId) {
        String result = parkingSlotService.allocateSlot(slotId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/deallocate/{slotId}")
    public ResponseEntity<String> deallocateSlot(@PathVariable Long slotId) {
        String result = parkingSlotService.deallocateSlot(slotId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{slotId}")
    public ResponseEntity<String> deleteSlot(@PathVariable Long slotId) {
        String result = parkingSlotService.deleteSlot(slotId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addSlot(@RequestBody ParkingSlot slot) {
        parkingSlotService.addSlot(slot);
        return ResponseEntity.ok("Slot added successfully!");
    }

    @GetMapping("/available")
    public ResponseEntity<Boolean> hasAvailableSlots() {
        boolean available = parkingSlotService.hasAvailableSlots();
        return ResponseEntity.ok(available);
    }
}