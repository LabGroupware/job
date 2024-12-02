package org.cresplanex.nova.job.config;

import org.cresplanex.nova.job.template.KeyValueTemplate;
import org.cresplanex.nova.job.template.RedisKeyValueTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class KeyValueTemplateConfiguration {

    @Bean
    public KeyValueTemplate keyValueTemplate(
            RedisTemplate<String, byte[]> redisTemplate
    ) {
        return new RedisKeyValueTemplate(redisTemplate);
    }
}
