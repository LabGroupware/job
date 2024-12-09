package org.cresplanex.nova.job.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter @Setter
@RequiredArgsConstructor
@Configuration
public class ApplicationConfiguration {

    @Value("${app.name}")
    private String name;

    @Value("${app.version}")
    private String version;

    @Value("${job.subscribe.failed}")
    private String failedSubscribe;

    @Value("${job.subscribe.processed}")
    private String processedSubscribe;

    @Value("${job.subscribe.success}")
    private String successSubscribe;

    @Value("${job.subscribe.initialized}")
    private String initializedSubscribe;

    public boolean isFailedSubscribe() {
        return Boolean.parseBoolean(failedSubscribe);
    }

    public boolean isProcessedSubscribe() {
        return Boolean.parseBoolean(processedSubscribe);
    }

    public boolean isSuccessSubscribe() {
        return Boolean.parseBoolean(successSubscribe);
    }

    public boolean isInitializedSubscribe() {
        return Boolean.parseBoolean(initializedSubscribe);
    }
}
