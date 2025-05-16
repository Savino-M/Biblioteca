package it.savinomarzano.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
public class ScheduledService implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {

            System.out.println("ciao");

        } catch (Exception e) {

        }

    }

}