package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.dtos.request.SubscribeRequest;
import com.example.demo.demoapi.dtos.response.MyWeatherResponseDTO;
import com.example.demo.demoapi.entity.Subscription;
import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.repositories.SubscriptionRepository;
import com.example.demo.demoapi.repositories.UserRepository;
import com.example.demo.demoapi.services.CityService;
import com.example.demo.demoapi.services.EmailSenderService;
import com.example.demo.demoapi.services.SubscriptionService;
import com.example.demo.demoapi.shared.EmailBaseParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final CityService cityService;

    @Override
    public void save(long userId, SubscribeRequest subscribeRequest) {
        log.info("getting user");
        Optional<User> opUser = userRepository.findById(userId);
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (subscribeRequest != null) {
                log.info("creating subscription");
                Subscription subscription = new Subscription(subscribeRequest.getType(), subscribeRequest.getCity(), user);
                subscriptionRepository.save(subscription);
                emailSenderService.sendEmail(user.getEmail(), EmailBaseParameters.SUB_SUBJECT, EmailBaseParameters.SUB_MESSAGE);
            } else {
                log.warn("subscribe request not set");
                throw new ApiRequestException("Subscribe details need to be added");
            }
        } else {
            log.warn("user doesn't exist");
            throw new ApiRequestException("User doesn't exist");
        }
    }

    @Override
    public void sendWeatherInformation() {
        log.info("Getting all subscriptions");
        List<Subscription> allSubscription = (List<Subscription>) subscriptionRepository.findAll();
        if (allSubscription.isEmpty()) {
            throw new ApiRequestException("You need to subscribe first!");
        }
        log.info("Sending email for subscribers");
        allSubscription.stream().forEach(subscription -> {
            MyWeatherResponseDTO responseDTO = cityService.getCityWeather(subscription.getCity());
            Optional<User> userOptional = userRepository.findById(subscription.getUser().getId());
            if (userOptional.isPresent()) {
                log.info("Getting user");
                User user = userOptional.get();
                emailSenderService.sendEmail(user.getEmail(), EmailBaseParameters.generateSubjectForMail(responseDTO.getName()), EmailBaseParameters.generateMessageForMail(responseDTO.getTemp(), responseDTO.getTempMax(), responseDTO.getTempMin(), responseDTO.getFeelsLike()));
            } else {
                log.warn("user doesn't exist");
                throw new ApiRequestException("User need to be subscribe firs!");
            }
        });
    }
}
