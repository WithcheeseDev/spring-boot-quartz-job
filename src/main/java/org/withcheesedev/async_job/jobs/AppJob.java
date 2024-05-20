package org.withcheesedev.async_job.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.withcheesedev.async_job.servicess.JobService;
import utils.DateUtil;

@Component
public class AppJob implements Job {

    private final JobService jobService;

    public AppJob(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Executing job " + context.getJobDetail().getDescription());
        System.out.println("Time is: " + DateUtil.getNoneUTCDateTime());

        jobService.handleOnValidateJob();
    }
}
