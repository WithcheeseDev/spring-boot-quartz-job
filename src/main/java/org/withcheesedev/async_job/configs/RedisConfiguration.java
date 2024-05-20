package org.withcheesedev.async_job.configs;

import lombok.Getter;
import lombok.Setter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration()
public class RedisConfiguration {

    @Bean
    public RedissonClient client() {
        /* Init redis configurations */
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");

        /* Init redisson client */
        return Redisson.create(config);
    }
}
