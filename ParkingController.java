package com.example.demo;

import com.example.demo.model.ParkingSlot;
import com.example.demo.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingSlotService service;

    // Display form to add a new slot
    @GetMapping("/addSlot")
    public String showAddSlotForm(Model model) {
        model.addAttribute("slot", new ParkingSlot());
        return "addSlot";
    }

    // Handle adding a new slot
    @PostMapping("/addSlot")
    public String addSlot(@ModelAttribute ParkingSlot slot, RedirectAttributes redirectAttributes) {
        service.addSlot(slot);
        redirectAttributes.addFlashAttribute("message", "New slot added successfully.");
        return "redirect:/service";
    }

    // Handle slot allocation
    @PostMapping("/allocateSlot")
    public String allocateSlot(@RequestParam("slotId") Long slotId, RedirectAttributes redirectAttributes) {
        String message = service.allocateSlot(slotId);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/service";
    }

    // Handle slot deallocation
    @PostMapping("/deallocateSlot")
    public String deallocateSlot(@RequestParam("slotId") Long slotId, RedirectAttributes redirectAttributes) {
        String message = service.deallocateSlot(slotId);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/service";
    }

    // Handle slot deletion
    @PostMapping("/deleteSlot")
    public String deleteSlot(@RequestParam("slotId") Long slotId, RedirectAttributes redirectAttributes) {
        String message = service.deleteSlot(slotId);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/service";
    }

    // Display available slots
    @GetMapping("/availableSlots")
    public String viewAvailableSlots(Model model) {
        model.addAttribute("slots", service.getAvailableSlots());
        return "availableSlots";
    }
}