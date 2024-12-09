package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.userprofile.UserProfileCreated;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.config.ApplicationConfiguration;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class UserProfileEventHandler {

    private final JobService jobService;

    private final ApplicationConfiguration applicationConfiguration;

    public DomainEventHandlers domainEventHandlers() {
        var handlerBuilder = DomainEventHandlersBuilder
                .forAggregateType(EventAggregateType.USER_PROFILE);

        if (applicationConfiguration.isInitializedSubscribe()) {
            handlerBuilder.onEvent(UserProfileCreated.BeginJobDomainEvent.class, this::handleBeginUserCreated, UserProfileCreated.BeginJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isProcessedSubscribe()) {
            handlerBuilder.onEvent(UserProfileCreated.ProcessedJobDomainEvent.class, this::handleProcessedUserCreated, UserProfileCreated.ProcessedJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isFailedSubscribe()) {
            handlerBuilder.onEvent(UserProfileCreated.FailedJobDomainEvent.class, this::handleFailedUserCreated, UserProfileCreated.FailedJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isSuccessSubscribe()) {
            handlerBuilder.onEvent(UserProfileCreated.SuccessJobDomainEvent.class, this::handleSuccessfullyUserCreated, UserProfileCreated.SuccessJobDomainEvent.TYPE);
        }

        return handlerBuilder.build();
    }

    private void handleBeginUserCreated(DomainEventEnvelope<UserProfileCreated.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), UserProfileCreated.BeginJobDomainEvent.TYPE);
    }

    private void handleProcessedUserCreated(DomainEventEnvelope<UserProfileCreated.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), UserProfileCreated.ProcessedJobDomainEvent.TYPE);
    }

    private void handleFailedUserCreated(DomainEventEnvelope<UserProfileCreated.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), UserProfileCreated.FailedJobDomainEvent.TYPE);
    }

    private void handleSuccessfullyUserCreated(DomainEventEnvelope<UserProfileCreated.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), UserProfileCreated.SuccessJobDomainEvent.TYPE);
    }
}
