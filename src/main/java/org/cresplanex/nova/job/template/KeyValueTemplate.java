package org.cresplanex.nova.job.template;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface KeyValueTemplate {
    void setValue(String key, byte[] value);
    void setValue(String key, byte[] value, long timeout, TimeUnit timeUnit);
    Long getExpire(String key, TimeUnit timeUnit);
    Optional<byte[]> getValue(String key);
    boolean exists(String key);
}
