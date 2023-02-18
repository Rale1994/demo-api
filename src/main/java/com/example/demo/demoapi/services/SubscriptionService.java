package com.example.demo.demoapi.services;

import com.example.demo.demoapi.dtos.request.SubscribeRequest;

public interface SubscriptionService {
    void save(long userId, SubscribeRequest subscribeRequest);
}
