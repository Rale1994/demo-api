package com.example.demo.demoapi.controller;

import com.example.demo.demoapi.dtos.response.MyWeatherResponseDTO;
import com.example.demo.demoapi.entity.City;
import com.example.demo.demoapi.services.CityService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("cities")
@Api
@AllArgsConstructor
public class CityController {

    private final CityService cityService;


    @GetMapping(path = "/all")
    public Page<MyWeatherResponseDTO> getWeatherAllCities(@QuerydslPredicate(root = City.class) Predicate predicate, Pageable pageable) {
        return cityService.getAll(predicate, pageable);
    }

    @GetMapping(path = "/{name}")
    public MyWeatherResponseDTO getWeatherCity(@PathVariable String name) {
        return cityService.getCityWeather(name);
    }

    @GetMapping(path = "/weather/{id}")
    public MyWeatherResponseDTO getById(@PathVariable long id) {
        return cityService.findById(id);
    }

    @Scheduled(fixedRateString = "PT01H")
    public void fetch() {
        cityService.fetchWeather();
    }

}
