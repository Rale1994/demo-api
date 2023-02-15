package com.example.demo.demoapi.resource;

import com.example.demo.demoapi.dtos.request.EmailMessageRequestDTO;
import com.example.demo.demoapi.services.EmailSenderService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
@Api
public class EmailController {

    private final EmailSenderService emailSenderService;

    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send")
    public ResponseEntity sendEmail(@RequestBody EmailMessageRequestDTO emailMessageRequestDTO){
        emailSenderService.sendEmail(emailMessageRequestDTO.getTo(), emailMessageRequestDTO.getSubject(), emailMessageRequestDTO.getMessage());
        return ResponseEntity.ok("Success");
    }
}
