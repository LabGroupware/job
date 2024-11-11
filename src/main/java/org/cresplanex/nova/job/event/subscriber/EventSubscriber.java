package org.cresplanex.nova.job.event.subscriber;

import lombok.AllArgsConstructor;
import org.cresplanex.core.events.subscriber.DomainEventDispatcher;
import org.cresplanex.core.events.subscriber.DomainEventDispatcherFactory;
import org.cresplanex.nova.job.config.ApplicationConfiguration;
import org.cresplanex.nova.job.event.EventAggregateChannel;
import org.cresplanex.nova.job.event.handler.UserProfileEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class EventSubscriber {

    private final ApplicationConfiguration applicationConfiguration;

    @Bean
    public DomainEventDispatcher domainEventDispatcher(
            UserProfileEventHandler userProfileEventHandler,
            DomainEventDispatcherFactory domainEventDispatcherFactory
    ) {
        return domainEventDispatcherFactory.make("%s.eventListener.%s".formatted(
                applicationConfiguration.getName(), EventAggregateChannel.USER_PROFILE), userProfileEventHandler.domainEventHandlers());
    }
}
