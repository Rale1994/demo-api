package com.example.demo.demoapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ApiException {
    private final String message;
    private final String timestamp;

}
