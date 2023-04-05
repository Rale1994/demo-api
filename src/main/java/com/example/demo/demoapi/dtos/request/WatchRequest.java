package com.example.demo.demoapi.dtos.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class WatchRequest {

    @NotBlank(message = "User is required")
    private long user;
    @NotBlank(message = "Brand is required")
    private String brand;
    @NotBlank(message = "Gender is required")
    private String gender;
    @NotBlank(message = "Country is required")
    private String country;

}
