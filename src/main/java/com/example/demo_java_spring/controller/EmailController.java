package com.example.demo_java_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo_java_spring.service.EmailService;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/api/v1/send-email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        emailService.sendSimpleMessage(to, subject, text);
        return "Email sent successfully";
    }
}
