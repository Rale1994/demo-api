package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.entity.Subscription;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.repositories.SubscriptionRepository;
import com.example.demo.demoapi.repositories.UserRepository;
import com.example.demo.demoapi.services.CityService;
import com.example.demo.demoapi.services.EmailSenderService;
import com.example.demo.demoapi.shared.EmailBaseParameters;
import com.example.demo.demoapi.utils.TestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EmailSenderService emailSenderService;
    @Mock
    private CityService cityService;
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;


    @Test
    public void testTryToSaveSubscription() {
        // GIVEN
        long userId = 1234l;
        var subscribeRequest = TestUtil.createSubscriptionRequest();
        var user = TestUtil.createUser();
        user.setId(userId);
        var subscription = TestUtil.createSubscription(subscribeRequest, user);

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        // ACTION
        subscriptionService.save(userId, subscribeRequest);

        // THEN
        assertNotNull(subscribeRequest);
        assertNotNull(userId);

        verify(emailSenderService, Mockito.times(1)).sendEmail(user.getEmail(), EmailBaseParameters.SUB_SUBJECT, EmailBaseParameters.SUB_MESSAGE);
    }

    @Test
    public void testTryToSaveSubscriptionWithoutUser() {
        // GIVEN
        long userId = 1234l;
        var subscribeRequest = TestUtil.createSubscriptionRequest();
        var user = TestUtil.createUser();
        user.setId(userId);

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(ApiRequestException.class, () -> subscriptionService.save(userId, subscribeRequest));
    }

    @Test
    public void testTryToSaveSubscriptionWithoutSubscriptionRequest() {
        // GIVEN
        long userId = 1234l;
        var user = TestUtil.createUser();
        user.setId(userId);

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // THEN
        assertThrows(ApiRequestException.class, () -> subscriptionService.save(userId, null));
    }

    @Test
    public void testTryToSendWeatherInformation() {
        // GIVEN
        long userId = 1234l;
        var myWeatherResponse = TestUtil.generateWeatherResponse();
        var user = TestUtil.createUser();
        user.setId(userId);
        List<Subscription> subscriptions = TestUtil.generateListOfSubscriptions();

        // WHEN
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        when(cityService.getCityWeather("Priboj")).thenReturn(myWeatherResponse);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // ACTION
        subscriptionService.sendWeatherInformation();

        // THEN
        assertNotNull(subscriptions);

        // VERIFY
        verify(emailSenderService, Mockito.times(1)).sendEmail(user.getEmail(), EmailBaseParameters.generateSubjectForMail(myWeatherResponse.getName()), EmailBaseParameters.generateMessageForMail(myWeatherResponse.getTemp(), myWeatherResponse.getTempMax(), myWeatherResponse.getTempMin(), myWeatherResponse.getFeelsLike()));
    }

    @Test
    public void testTryToSendWeatherInformationWithoutSubscription() {
        // GIVE
        List<Subscription> emptyLis = new ArrayList<>();

        // WHEN
        when(subscriptionRepository.findAll()).thenReturn(emptyLis);

        // THEN
        assertThrows(ApiRequestException.class, () -> subscriptionService.sendWeatherInformation());
    }

    @Test
    public void testTryToSendWeatherInformationWithoutUser() {
        // GIVEN
        long userId = 1234l;
        var myWeatherResponse = TestUtil.generateWeatherResponse();
        var user = TestUtil.createUser();
        user.setId(userId);
        List<Subscription> subscriptions = TestUtil.generateListOfSubscriptions();

        // WHEN
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        when(cityService.getCityWeather("Priboj")).thenReturn(myWeatherResponse);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(ApiRequestException.class, () -> subscriptionService.sendWeatherInformation());
    }
}