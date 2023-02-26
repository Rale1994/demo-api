package com.example.demo.demoapi.quartz;

import com.example.demo.demoapi.services.SubscriptionService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendEmailJob implements Job {

    @Autowired
    private SubscriptionService subscriptionService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        subscriptionService.sendWeatherInformation();
    }
}
