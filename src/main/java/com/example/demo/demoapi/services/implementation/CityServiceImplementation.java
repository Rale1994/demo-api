package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.dtos.response.MyWeatherResponseDTO;
import com.example.demo.demoapi.dtos.response.openWeather.OpenWeatherResponseDTO;
import com.example.demo.demoapi.entity.City;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.repositories.CityRepository;
import com.example.demo.demoapi.services.CityService;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CityServiceImplementation implements CityService {

    private final String OPEN_WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather?q={}&APPID=61fbcbce338905c8df0553683941ec03";
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;
    private final CityRepository cityRepository;

    @Override
    public MyWeatherResponseDTO getCityWeather(String name) {
        String NEW_API_URL = OPEN_WEATHER_API_URL.replace("{}", name.toUpperCase());

        OpenWeatherResponseDTO response = restTemplate.exchange(NEW_API_URL, HttpMethod.GET, null, OpenWeatherResponseDTO.class).getBody();
        City city= cityRepository.findByName(name);
        MyWeatherResponseDTO myNewWeatherResponseDTO;
        if (city != null) {
            city.updateWeather(response);
        } else {
            city= new City(response);
        }
        cityRepository.save(city);
        myNewWeatherResponseDTO = new MyWeatherResponseDTO(city);
        return myNewWeatherResponseDTO;
    }

    @Override
    public Page<MyWeatherResponseDTO> getAll(Predicate predicate, Pageable pageable) {

        Page<City> weathers = cityRepository.findAll(predicate, pageable);

        List<MyWeatherResponseDTO> weathersStream = weathers.stream().map(weather -> modelMapper.map(weather, MyWeatherResponseDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(weathersStream, pageable, weathers.getTotalElements());
    }

    @Override
    public MyWeatherResponseDTO findById(long id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        if (!cityOptional.isPresent()) {
            throw new ApiRequestException("City with id:" + id + " does not exist!");
        }
        return modelMapper.map(cityOptional.get(), MyWeatherResponseDTO.class);
    }

    @Override
    public void fetchWeather() {
        String[] cities = new String[]{"Paris", "Berlin", "Milano", "Priboj"};
        String OPEN_WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather?q={}&APPID=61fbcbce338905c8df0553683941ec03";
        for (String s : cities) {
            String NEW_API_URL = OPEN_WEATHER_API_URL.replace("{}", s.toUpperCase());
            OpenWeatherResponseDTO response = restTemplate.exchange(NEW_API_URL, HttpMethod.GET, null, OpenWeatherResponseDTO.class).getBody();//optional
            City city = cityRepository.findByName(response.getName());
            if (city != null) {
                city.updateWeather(response);
            } else {
                city = new City(response);
            }
            cityRepository.save(city);
        }

    }

}
