package org.cresplanex.nova.job.event.subscriber;

import lombok.AllArgsConstructor;
import org.cresplanex.api.state.common.constants.ServiceType;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.subscriber.BaseSubscriber;
import org.cresplanex.core.events.subscriber.DomainEventDispatcher;
import org.cresplanex.core.events.subscriber.DomainEventDispatcherFactory;
import org.cresplanex.nova.job.config.ApplicationConfiguration;
import org.cresplanex.nova.job.event.handler.UserProfileEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class EventSubscriber extends BaseSubscriber {

    private final ApplicationConfiguration applicationConfiguration;

    @Bean
    public DomainEventDispatcher domainEventDispatcher(
            UserProfileEventHandler userProfileEventHandler,
            DomainEventDispatcherFactory domainEventDispatcherFactory
    ) {
        return domainEventDispatcherFactory.make(
                this.getDispatcherId(ServiceType.NOVA_JOB, EventAggregateType.USER_PROFILE),
                userProfileEventHandler.domainEventHandlers()
        );
    }
}
