package com.example.demo.demoapi.services;

import com.example.demo.demoapi.dtos.response.MyWeatherResponseDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {


    MyWeatherResponseDTO getCityWeather(String name);

    Page<MyWeatherResponseDTO> getAll(Predicate predicate, Pageable pageable);

    MyWeatherResponseDTO findById(long id);

    void fetchWeather();
}
