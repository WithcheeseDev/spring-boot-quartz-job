package org.withcheesedev.async_job.servicess;

import jakarta.annotation.PostConstruct;
import org.quartz.SchedulerException;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import utils.DateUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class JobService {
    private final RedissonClient redissonClient;

    public JobService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @PostConstruct
    private void runJob() throws InterruptedException, SchedulerException {
        // Create a new job
        createJob();
    }

    private void initJob() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handleOnValidateJob();
            }
        }).start();
//        TimeUnit.MILLISECONDS.sleep(50);
    }

    private void createJob() {
        Map<String, String> newJob = new HashMap<>();
        newJob.put("status", "Pending");
        newJob.put("createdDate", DateUtil.getNoneUTCDateTime());

        RMap<String, String> jobCollection = redissonClient.getMap("jobs");
        jobCollection.putAll(newJob);
    }

    public void handleOnValidateJob() {
        RLock lock = redissonClient.getLock("lock");

        lock.lock();

        RMap<String, String> jobCollection = redissonClient.getMap("jobs");
        if (jobCollection.get("status").equals("Pending")) {
            jobCollection.put("status", "Success");
            System.out.println("Hello World!");
        }

        lock.unlock();
    }
}
