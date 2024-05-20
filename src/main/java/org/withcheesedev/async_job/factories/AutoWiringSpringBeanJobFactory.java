package org.withcheesedev.async_job.factories;

import org.quartz.Job;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public class AutoWiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(AutoWiringSpringBeanJobFactory.class);

    private transient AutowireCapableBeanFactory beanFactory;

    private final ApplicationContext context;

    public AutoWiringSpringBeanJobFactory(ApplicationContext context) {
        this.context = context;
    }

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        beanFactory = context.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
