package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;


    @Override
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
        simpleMailMessage.setFrom("golubovicrados@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        this.mailSender.send(simpleMailMessage);
    }
}
