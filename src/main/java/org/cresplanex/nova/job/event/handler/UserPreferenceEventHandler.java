package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.userpreference.UserPreferenceUpdated;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.config.ApplicationConfiguration;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class UserPreferenceEventHandler {

    private final JobService jobService;

    private final ApplicationConfiguration applicationConfiguration;

    public DomainEventHandlers domainEventHandlers() {
        var handlerBuilder = DomainEventHandlersBuilder
                .forAggregateType(EventAggregateType.USER_PREFERENCE);

        if (applicationConfiguration.isInitializedSubscribe()) {
            handlerBuilder.onEvent(UserPreferenceUpdated.BeginJobDomainEvent.class, this::handleBeginUserPreferenceUpdated, UserPreferenceUpdated.BeginJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isProcessedSubscribe()) {
            handlerBuilder.onEvent(UserPreferenceUpdated.ProcessedJobDomainEvent.class, this::handleProcessedUserPreferenceUpdated, UserPreferenceUpdated.ProcessedJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isFailedSubscribe()) {
            handlerBuilder.onEvent(UserPreferenceUpdated.FailedJobDomainEvent.class, this::handleFailedUserPreferenceUpdated, UserPreferenceUpdated.FailedJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isSuccessSubscribe()) {
            handlerBuilder.onEvent(UserPreferenceUpdated.SuccessJobDomainEvent.class, this::handleSuccessfullyUserPreferenceUpdated, UserPreferenceUpdated.SuccessJobDomainEvent.TYPE);
        }

        return handlerBuilder.build();
    }

    private void handleBeginUserPreferenceUpdated(DomainEventEnvelope<UserPreferenceUpdated.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), UserPreferenceUpdated.BeginJobDomainEvent.TYPE);
    }

    private void handleProcessedUserPreferenceUpdated(DomainEventEnvelope<UserPreferenceUpdated.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), UserPreferenceUpdated.ProcessedJobDomainEvent.TYPE);
    }

    private void handleFailedUserPreferenceUpdated(DomainEventEnvelope<UserPreferenceUpdated.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), UserPreferenceUpdated.FailedJobDomainEvent.TYPE);
    }

    private void handleSuccessfullyUserPreferenceUpdated(DomainEventEnvelope<UserPreferenceUpdated.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), UserPreferenceUpdated.SuccessJobDomainEvent.TYPE);
    }
}
