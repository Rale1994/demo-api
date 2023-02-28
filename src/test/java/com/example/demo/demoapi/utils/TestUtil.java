package com.example.demo.demoapi.utils;

import com.example.demo.demoapi.dtos.request.SubscribeRequest;
import com.example.demo.demoapi.dtos.response.MyWeatherResponseDTO;
import com.example.demo.demoapi.entity.Subscription;
import com.example.demo.demoapi.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static SubscribeRequest createSubscriptionRequest() {
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setCity("Priboj");
        subscribeRequest.setType("test-type");

        return subscribeRequest;
    }

    public static User createUser() {
        User user = new User();
        user.setId(1234);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setRole("ADMIN");
        user.setEmail("emailexample@example.com");
        user.setPassword("password");

        return user;
    }

    public static Subscription createSubscription(SubscribeRequest subscribeRequest, User user) {
        Subscription subscription = new Subscription();
        subscription.setCity(subscribeRequest.getCity());
        subscription.setType(subscribeRequest.getType());
        subscription.setUser(user);

        return subscription;

    }

    public static List<Subscription> generateListOfSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        Subscription subscription = new Subscription();
        subscription.setId(1234L);
        subscription.setType("test");
        subscription.setCity("Priboj");
        User user = createUser();
        subscription.setUser(user);

        subscriptions.add(subscription);
        return subscriptions;
    }

    public static MyWeatherResponseDTO generateWeatherResponse() {
        MyWeatherResponseDTO myWeatherResponseDTO = new MyWeatherResponseDTO();
        myWeatherResponseDTO.setDescription("desc");
        myWeatherResponseDTO.setTemp(5);
        myWeatherResponseDTO.setTempMax(10);
        myWeatherResponseDTO.setTempMin(5);
        myWeatherResponseDTO.setName("name");
        myWeatherResponseDTO.setFeelsLike(4);
        myWeatherResponseDTO.setLastWeatherInfoDate(LocalDateTime.now());
        return myWeatherResponseDTO;

    }
}
