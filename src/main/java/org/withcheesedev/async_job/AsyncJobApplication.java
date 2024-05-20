package org.withcheesedev.async_job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.withcheesedev.async_job.configs.schedulers.SchedulerConfiguration;

@Import({SchedulerConfiguration.class})
@SpringBootApplication
public class AsyncJobApplication {


    public static void main(String[] args) {
        SpringApplication.run(AsyncJobApplication.class, args);
    }
}
