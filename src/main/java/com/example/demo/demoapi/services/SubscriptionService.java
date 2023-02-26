package com.example.demo.demoapi.services;

import com.example.demo.demoapi.dtos.request.SubscribeRequest;
import com.example.demo.demoapi.dtos.response.MyWeatherResponseDTO;

public interface SubscriptionService {
    void save(long userId, SubscribeRequest subscribeRequest);

    void sendWeatherInformation();
}
