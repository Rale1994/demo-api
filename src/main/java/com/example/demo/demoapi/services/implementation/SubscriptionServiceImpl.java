package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.dtos.request.SubscribeRequest;
import com.example.demo.demoapi.entity.Subscription;
import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.repositories.SubscriptionRepository;
import com.example.demo.demoapi.repositories.UserRepository;
import com.example.demo.demoapi.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Override
    public void save(long userId, SubscribeRequest subscribeRequest) {
        Optional<User> opUser= userRepository.findById(userId);
        User user = opUser.get();
        Subscription subscription= new Subscription(subscribeRequest.getType(), user);
        subscriptionRepository.save(subscription);
    }
}
