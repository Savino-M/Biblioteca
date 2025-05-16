package it.savinomarzano.configuration;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.quartz.CronScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import it.savinomarzano.service.ScheduledService;

@Configuration
public class QuartzConfig {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Bean
    JobDetail scheduledJobDetail() {
        return JobBuilder.newJob(ScheduledService.class)
                .withIdentity("scheduledJob")
                .storeDurably()
                .build();
    }

    @Bean
    Trigger scheduledJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(scheduledJobDetail())
                .withIdentity("scheduledJobTrigger")
                .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(19, 9))
                .build();
    }

    @Bean
    Scheduler scheduler(SchedulerFactoryBean factory) throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(scheduledJobDetail(), scheduledJobTrigger());
        return scheduler;
    }

    @Bean
    JobFactory jobFactory() {
        return new SpringBeanJobFactory() {
            @Override
            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
                final Object job = super.createJobInstance(bundle);
                beanFactory.autowireBean(job);
                return job;
            }
        };
    }

    @Bean
    SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(jobFactory);
        return factory;
    }

}
