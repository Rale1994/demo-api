package com.example.demo.demoapi.dtos.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class EmailMessageRequest {
    private String to;
    private String subject;
    private String message;
}
