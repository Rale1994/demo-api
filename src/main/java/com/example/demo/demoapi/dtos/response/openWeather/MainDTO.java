package com.example.demo.demoapi.dtos.response.openWeather;

import lombok.Data;

@Data
public class MainDTO {
    private int temp;
    private int feelsLike;
    private int tempMin;
    private int tempMax;
}
