package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.event.EventAggregateChannel;
import org.cresplanex.nova.job.event.model.userprofile.BeginCreateUserProfile;
import org.cresplanex.nova.job.event.model.userprofile.FailedCreateUserProfile;
import org.cresplanex.nova.job.event.model.userprofile.ProcessedCreateUserProfile;
import org.cresplanex.nova.job.event.model.userprofile.SuccessfullyCreateUserProfile;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class UserProfileEventHandler {

    private final JobService jobService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(EventAggregateChannel.USER_PROFILE)
                .onEvent(BeginCreateUserProfile.class, this::handleBeginUserCreated, BeginCreateUserProfile.TYPE)
                .onEvent(ProcessedCreateUserProfile.class, this::handleProcessedUserCreated, ProcessedCreateUserProfile.TYPE)
                .onEvent(FailedCreateUserProfile.class, this::handleFailedUserCreated, FailedCreateUserProfile.TYPE)
                .onEvent(SuccessfullyCreateUserProfile.class, this::handleSuccessfullyUserCreated, SuccessfullyCreateUserProfile.TYPE)
                .build();
    }

    private void handleBeginUserCreated(DomainEventEnvelope<BeginCreateUserProfile> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleProcessedUserCreated(DomainEventEnvelope<ProcessedCreateUserProfile> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleFailedUserCreated(DomainEventEnvelope<FailedCreateUserProfile> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleSuccessfullyUserCreated(DomainEventEnvelope<SuccessfullyCreateUserProfile> dee) {
        jobService.update(dee.getEvent());
    }
}
