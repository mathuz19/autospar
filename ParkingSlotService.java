package com.example.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.ParkingSlot;
import com.example.demo.repository.ParkingSlotRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    // Custom Stack implementation
    private static class CustomStack {
        private final ParkingSlot[] slots;
        private int top;
        private final int capacity;

        public CustomStack(int size) {
            slots = new ParkingSlot[size];
            top = -1;
            capacity = size;
        }

        public void push(ParkingSlot slot) {
            if (top < capacity - 1) {
                slots[++top] = slot;
            }
        }

        public ParkingSlot pop() {
            if (top >= 0) {
                ParkingSlot slot = slots[top];
                slots[top--] = null;
                return slot;
            }
            return null;
        }

        public void remove(ParkingSlot slot) {
            for (int i = 0; i <= top; i++) {
                if (slots[i].equals(slot)) {
                    for (int j = i; j < top; j++) {
                        slots[j] = slots[j + 1];
                    }
                    slots[top--] = null;
                    break;
                }
            }
        }

        public boolean isEmpty() {
            return top == -1;
        }
    }

    private final CustomStack availableSlotsStack;

    public ParkingSlotService(ParkingSlotRepository parkingSlotRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.availableSlotsStack = new CustomStack(100); // Arbitrary size, adjust as needed
        initializeStack();
    }

    private void initializeStack() {
        ParkingSlot[] allSlots = parkingSlotRepository.findAll().toArray(ParkingSlot[]::new);
        for (ParkingSlot slot : allSlots) {
            if (!slot.isOccupied()) {
                availableSlotsStack.push(slot);
            }
        }
    }

    public String allocateSlot() {
        ParkingSlot slot = availableSlotsStack.pop();
        if (slot == null) {
            return "No available slots!";
        }
        slot.setOccupied(true);
        parkingSlotRepository.save(slot);
        return "Slot " + slot.getId() + " allocated successfully!";
    }

    public String allocateSlot(Long slotId) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        if (slot.isOccupied()) {
            return "Slot is already occupied!";
        }
        slot.setOccupied(true);
        parkingSlotRepository.save(slot);
        availableSlotsStack.remove(slot);
        return "Slot allocated successfully!";
    }

    public String deallocateSlot(Long slotId) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        if (!slot.isOccupied()) {
            return "Slot is already available!";
        }
        slot.setOccupied(false);
        parkingSlotRepository.save(slot);
        availableSlotsStack.push(slot);
        return "Slot deallocated successfully!";
    }

    public String deleteSlot(Long slotId) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        parkingSlotRepository.delete(slot);
        availableSlotsStack.remove(slot);
        return "Slot deleted successfully!";
    }

    public List<ParkingSlot> getAllSlotsSortedByAvailability() {
        ParkingSlot[] slots = parkingSlotRepository.findAll().toArray(ParkingSlot[]::new);
        quickSort(slots, 0, slots.length - 1);
        return Arrays.asList(slots);
    }

    public boolean hasAvailableSlots() {
        return !availableSlotsStack.isEmpty();
    }

    public void addSlot(ParkingSlot slot) {
        parkingSlotRepository.save(slot);
        if (!slot.isOccupied()) {
            availableSlotsStack.push(slot);
        }
    }

    private void quickSort(ParkingSlot[] slots, int low, int high) {
        if (low < high) {
            int pi = partition(slots, low, high);
            quickSort(slots, low, pi - 1);
            quickSort(slots, pi + 1, high);
        }
    }

    private int partition(ParkingSlot[] slots, int low, int high) {
        ParkingSlot pivot = slots[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (!slots[j].isOccupied() || (slots[j].isOccupied() && pivot.isOccupied())) {
                i++;
                ParkingSlot temp = slots[i];
                slots[i] = slots[j];
                slots[j] = temp;
            }
        }
        ParkingSlot temp = slots[i + 1];
        slots[i + 1] = slots[high];
        slots[high] = temp;
        return i + 1;
    }

    @PostConstruct
    public void init() {
        if (parkingSlotRepository.count() == 0) {
            ParkingSlot slot1 = new ParkingSlot("Car", "Regular", false);
            ParkingSlot slot2 = new ParkingSlot("Motorcycle", "Reserved", true);
            ParkingSlot slot3 = new ParkingSlot("Bus", "Large", false);
            parkingSlotRepository.save(slot1);
            parkingSlotRepository.save(slot2);
            parkingSlotRepository.save(slot3);
        }
    }
}