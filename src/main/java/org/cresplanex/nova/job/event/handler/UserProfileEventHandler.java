package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.userprofile.UserProfileCreated;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class UserProfileEventHandler {

    private final JobService jobService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(EventAggregateType.USER_PROFILE)
                .onEvent(UserProfileCreated.BeginJobDomainEvent.class, this::handleBeginUserCreated, UserProfileCreated.BeginJobDomainEvent.TYPE)
                .onEvent(UserProfileCreated.ProcessedJobDomainEvent.class, this::handleProcessedUserCreated, UserProfileCreated.ProcessedJobDomainEvent.TYPE)
                .onEvent(UserProfileCreated.FailedJobDomainEvent.class, this::handleFailedUserCreated, UserProfileCreated.FailedJobDomainEvent.TYPE)
                .onEvent(UserProfileCreated.SuccessJobDomainEvent.class, this::handleSuccessfullyUserCreated, UserProfileCreated.SuccessJobDomainEvent.TYPE)
                .build();
    }

    private void handleBeginUserCreated(DomainEventEnvelope<UserProfileCreated.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleProcessedUserCreated(DomainEventEnvelope<UserProfileCreated.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleFailedUserCreated(DomainEventEnvelope<UserProfileCreated.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleSuccessfullyUserCreated(DomainEventEnvelope<UserProfileCreated.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }
}
