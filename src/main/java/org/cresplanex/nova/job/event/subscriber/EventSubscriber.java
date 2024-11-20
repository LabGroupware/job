package org.cresplanex.nova.job.event.subscriber;

import lombok.AllArgsConstructor;
import org.cresplanex.api.state.common.constants.ServiceType;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.subscriber.BaseSubscriber;
import org.cresplanex.core.events.subscriber.DomainEventDispatcher;
import org.cresplanex.core.events.subscriber.DomainEventDispatcherFactory;
import org.cresplanex.nova.job.config.ApplicationConfiguration;
import org.cresplanex.nova.job.event.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class EventSubscriber extends BaseSubscriber {

    private final ApplicationConfiguration applicationConfiguration;

    @Bean
    public DomainEventDispatcher userProfileDomainEventDispatcher(
            UserProfileEventHandler userProfileEventHandler,
            DomainEventDispatcherFactory domainEventDispatcherFactory
    ) {
        return domainEventDispatcherFactory.make(
                this.getDispatcherId(ServiceType.NOVA_JOB, EventAggregateType.USER_PROFILE),
                userProfileEventHandler.domainEventHandlers()
        );
    }

    @Bean
    public DomainEventDispatcher userPreferenceDomainEventDispatcher(
            UserPreferenceEventHandler userPreferenceEventHandler,
            DomainEventDispatcherFactory domainEventDispatcherFactory
    ) {
        return domainEventDispatcherFactory.make(
                this.getDispatcherId(ServiceType.NOVA_JOB, EventAggregateType.USER_PREFERENCE),
                userPreferenceEventHandler.domainEventHandlers()
        );
    }

    @Bean
    public DomainEventDispatcher organizationDomainEventDispatcher(
            OrganizationEventHandler organizationEventHandler,
            DomainEventDispatcherFactory domainEventDispatcherFactory
    ) {
        return domainEventDispatcherFactory.make(
                this.getDispatcherId(ServiceType.NOVA_JOB, EventAggregateType.ORGANIZATION),
                organizationEventHandler.domainEventHandlers()
        );
    }

    @Bean
    public DomainEventDispatcher fileObjectDomainEventDispatcher(
            FileObjectEventHandler fileObjectEventHandler,
            DomainEventDispatcherFactory domainEventDispatcherFactory
    ) {
        return domainEventDispatcherFactory.make(
                this.getDispatcherId(ServiceType.NOVA_JOB, EventAggregateType.STORAGE_OBJECT),
                fileObjectEventHandler.domainEventHandlers()
        );
    }

    @Bean
    public DomainEventDispatcher taskDomainEventDispatcher(
            TaskEventHandler taskEventHandler,
            DomainEventDispatcherFactory domainEventDispatcherFactory
    ) {
        return domainEventDispatcherFactory.make(
                this.getDispatcherId(ServiceType.NOVA_JOB, EventAggregateType.PLAN_TASK),
                taskEventHandler.domainEventHandlers()
        );
    }

    @Bean
    public DomainEventDispatcher teamDomainEventDispatcher(
            TeamEventHandler teamEventHandler,
            DomainEventDispatcherFactory domainEventDispatcherFactory
    ) {
        return domainEventDispatcherFactory.make(
                this.getDispatcherId(ServiceType.NOVA_JOB, EventAggregateType.TEAM),
                teamEventHandler.domainEventHandlers()
        );
    }
}
