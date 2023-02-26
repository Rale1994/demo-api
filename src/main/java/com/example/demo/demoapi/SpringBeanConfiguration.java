package com.example.demo.demoapi;

import com.example.demo.demoapi.quartz.SendEmailJob;
import org.quartz.*;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
public class SpringBeanConfiguration {

    private final ApplicationContext applicationContext;

    public SpringBeanConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
//    @Bean
//    public JobDetail jobDetail() {
//        return JobBuilder.newJob().ofType(SendEmailJob.class)
//                .storeDurably()
//                .withIdentity("Qrtz_Job_Detail")
//                .withDescription("Invoke Sample Job service...")
//                .build();
//    }
//
//
//    @Bean
//    public Trigger trigger(JobDetail job) {
//        return TriggerBuilder.newTrigger().forJob(job)
//                .withIdentity("Qrtz_Trigger")
//                .withDescription("Sample trigger")
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1))
//                .build();
//    }


    @Bean
    public SimpleTriggerFactoryBean createSimpleTriggerFactoryBean(JobDetail jobDetail) {
        SimpleTriggerFactoryBean simpleTriggerFactory = new SimpleTriggerFactoryBean();

        simpleTriggerFactory.setJobDetail(jobDetail);
        simpleTriggerFactory.setStartDelay(0);
        simpleTriggerFactory.setRepeatInterval(3600000);
        // simpleTriggerFactory.setRepeatCount(10);
        return simpleTriggerFactory;
    }

    @Bean
    public JobDetailFactoryBean createJobDetailFactoryBean() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(SendEmailJob.class);
        return jobDetailFactory;
    }

    @Bean
    SpringBeanJobFactory createSpringBeanJobFactory() {
        return new SpringBeanJobFactory() {
            @Override
            protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {

                final Object job = super.createJobInstance(bundle);

                applicationContext.getAutowireCapableBeanFactory().autowireBean(job);

                return job;
            }
        };
    }

    @Bean
    public SchedulerFactoryBean createSchedulerFactory(SpringBeanJobFactory springBeanJobFactory, Trigger trigger) {

        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactory.setTriggers(trigger);

        springBeanJobFactory.setApplicationContext(applicationContext);
        schedulerFactory.setJobFactory(springBeanJobFactory);

        return schedulerFactory;
    }
}
