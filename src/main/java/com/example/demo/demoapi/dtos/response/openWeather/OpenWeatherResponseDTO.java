package com.example.demo.demoapi.dtos.response.openWeather;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OpenWeatherResponseDTO {

     private String name;

     private List<WeatherDTO> weather = new ArrayList<>();

     private Map<String, Object> main = new HashMap<>();



}
