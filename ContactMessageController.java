package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contacts")
public class ContactMessageController {

    @Autowired
    private ContactMessageRepository repository;

    @PostMapping
    public ResponseEntity<String> createContactMessage(@RequestBody ContactMessage contactMessage) {
        repository.save(contactMessage);
        return ResponseEntity.ok("Contact message submitted successfully");
    }
}