package com.example.demo.demoapi.entity;

import com.example.demo.demoapi.dtos.response.openWeather.OpenWeatherResponseDTO;
import com.example.demo.demoapi.dtos.response.openWeather.WeatherDTO;
import com.example.demo.demoapi.shared.Utils;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity(name = "city")
public class City {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private int temp;
    private int feelsLike;
    private int tempMin;
    private int tempMax;
    private String weatherDescription;

    private LocalDateTime lastWeatherInfoDate;

    public City() {
    }

    public City(OpenWeatherResponseDTO openWeatherResponseDTO) {
        this.name = openWeatherResponseDTO.getName();
        updateWeather(openWeatherResponseDTO);

    }

    public void updateWeather(OpenWeatherResponseDTO openWeatherResponseDTO) {
        Double temp = Utils.convertToCelsius((Double) openWeatherResponseDTO.getMain().get("temp"));
        Double feelsLike = Utils.convertToCelsius((Double) openWeatherResponseDTO.getMain().get("feels_like"));
        Double tempMax = Utils.convertToCelsius((Double) openWeatherResponseDTO.getMain().get("temp_max"));
        Double tempMin = Utils.convertToCelsius((Double) openWeatherResponseDTO.getMain().get("temp_min"));


        this.temp = temp.intValue();
        this.feelsLike = feelsLike.intValue();
        this.tempMin = tempMin.intValue();
        this.tempMax = tempMax.intValue();

        List<WeatherDTO> listWeather = openWeatherResponseDTO.getWeather();
        String description = listWeather.stream().map(desc -> desc.getDescription()).collect(Collectors.joining(",")).toString();
        this.weatherDescription = description;
        this.lastWeatherInfoDate = LocalDateTime.now();

    }
}
