package org.withcheesedev.async_job.usecases;

import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.stereotype.Component;
import org.withcheesedev.async_job.jobs.AppJob;

import java.util.Date;
import java.util.UUID;

public class CreateJobSchedulerUseCase {
    private final Scheduler scheduler;

    public CreateJobSchedulerUseCase(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void execute(Date broadcastTime) throws SchedulerException {
        JobDetail jobDetail = createJobDetail();
        Trigger jobTrigger = createTrigger(jobDetail, broadcastTime);
        scheduler.scheduleJob(jobDetail, jobTrigger);
    }

    private JobDetail createJobDetail() {
        return JobBuilder.newJob(AppJob.class)
                .withIdentity(UUID.randomUUID().toString(), "group-job")
                .withDescription("Create App Job")
                .build();
    }

    private Trigger createTrigger(JobDetail jobDetail, Date targetDate) {
        return TriggerBuilder.newTrigger()
                .withIdentity(UUID.randomUUID().toString(), "group")
                .withDescription("Description for trigger")
                .startNow()
                .build();
    }
}
