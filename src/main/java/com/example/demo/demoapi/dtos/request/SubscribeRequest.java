package com.example.demo.demoapi.dtos.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SubscribeRequest {

    @NotBlank(message = "Type need to be set")
    private String type;

    @NotBlank(message = "City need to be set")
    private String city;
}
