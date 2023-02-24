package com.example.demo.demoapi.resource;

import com.example.demo.demoapi.dtos.request.SubscribeRequest;
import com.example.demo.demoapi.services.SubscriptionService;
import com.example.demo.demoapi.shared.Constants;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(Constants.BASE_URL + "/subscribe")
@Api
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private Scheduler scheduler;

    public SubscriptionController(SubscriptionService subscriptionService, Scheduler scheduler) {
        this.subscriptionService = subscriptionService;
        this.scheduler = scheduler;
    }

    @PostMapping("/create/{userId}")
    public void createSubscription(@PathVariable long userId, @RequestBody SubscribeRequest subscribeRequest) {
        subscriptionService.save(userId, subscribeRequest);
    }

    @GetMapping("/send")
    public ResponseEntity sendWeatherInformation() {
        subscriptionService.sendWeatherInformation();
        return ResponseEntity.ok("Email with weather information is sent");
    }

}
