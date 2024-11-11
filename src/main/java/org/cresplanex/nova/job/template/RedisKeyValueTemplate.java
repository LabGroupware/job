package org.cresplanex.nova.job.template;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class RedisKeyValueTemplate implements KeyValueTemplate {

    private RedisTemplate<String, byte[]> redisTemplate;

    @Override
    public void setValue(String key, byte[] value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setValue(String key, byte[] value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public Optional<byte[]> getValue(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
