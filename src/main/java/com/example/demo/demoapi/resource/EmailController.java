package com.example.demo.demoapi.resource;

import com.example.demo.demoapi.dtos.request.EmailMessageRequest;
import com.example.demo.demoapi.services.EmailSenderService;
import com.example.demo.demoapi.shared.Constants;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.BASE_URL+"email")
@Api
public class EmailController {

    private final EmailSenderService emailSenderService;

    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send")
    public ResponseEntity sendEmail(@RequestBody EmailMessageRequest emailMessageRequest){
        emailSenderService.sendEmail(emailMessageRequest.getTo(), emailMessageRequest.getSubject(), emailMessageRequest.getMessage());
        return ResponseEntity.ok("Success");
    }
}
