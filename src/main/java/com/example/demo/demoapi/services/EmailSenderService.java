package com.example.demo.demoapi.services;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}
