package org.withcheesedev.async_job.configs.schedulers;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.withcheesedev.async_job.factories.AutoWiringSpringBeanJobFactory;
import org.withcheesedev.async_job.jobs.AppJob;

import java.io.IOException;
import java.util.List;
import java.util.Properties;


@Configuration
public class SchedulerConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulerConfiguration.class);

    private final ApplicationContext context;

    public SchedulerConfiguration(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(List<Trigger> triggers) throws IOException {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();

        /* Set JobFactory */
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory(context);
        jobFactory.setApplicationContext(context);
        schedulerFactory.setJobFactory(jobFactory);

        /* Set Quartz properties */
        schedulerFactory.setQuartzProperties(quartzProperties());

        /* Set Job triggers */
        schedulerFactory.setTriggers(triggers.toArray(new Trigger[0]));
        LOG.info("Starting scheduler...");
        return schedulerFactory;
    }

    /*
     * First Job
     * */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        return getJobDetailFactoryBean("First job");
    }

    @Bean
    public SimpleTriggerFactoryBean jobTrigger(@Qualifier("jobDetailFactoryBean") JobDetail jobDetail) {
        return getJobTriggerFactoryBean(jobDetail);
    }

    /*
     * Second Job
     * */
    @Bean
    public JobDetailFactoryBean secondJobDetailFactoryBean() {
        return getJobDetailFactoryBean("Second job");
    }

    @Bean
    public SimpleTriggerFactoryBean secondJobTrigger(@Qualifier("secondJobDetailFactoryBean") JobDetail jobDetail) {
        return getJobTriggerFactoryBean(jobDetail);
    }

    /*
     * Third Job
     * */
    @Bean
    public JobDetailFactoryBean thirdJobDetailFactoryBean() {
        return getJobDetailFactoryBean("Third job");
    }

    @Bean
    public SimpleTriggerFactoryBean thirdJobTrigger(@Qualifier("thirdJobDetailFactoryBean") JobDetail jobDetail) {
        return getJobTriggerFactoryBean(jobDetail);
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        // Load configurations from quartz.properties
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    private JobDetailFactoryBean getJobDetailFactoryBean(String description) {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(AppJob.class); // Specify your job class
        jobDetailFactory.setDurability(true);
        jobDetailFactory.setDescription(description);

        return jobDetailFactory;
    }

    private SimpleTriggerFactoryBean getJobTriggerFactoryBean(JobDetail jobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setStartDelay(0);
        trigger.setRepeatInterval(5000);

        return trigger;
    }
}
