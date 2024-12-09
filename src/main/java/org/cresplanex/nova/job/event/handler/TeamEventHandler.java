package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.team.TeamAddedUsers;
import org.cresplanex.api.state.common.event.model.team.TeamCreated;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.config.ApplicationConfiguration;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class TeamEventHandler {

    private final JobService jobService;

    private final ApplicationConfiguration applicationConfiguration;

    public DomainEventHandlers domainEventHandlers() {
        var handlerBuilder = DomainEventHandlersBuilder
                .forAggregateType(EventAggregateType.TEAM);

        if (applicationConfiguration.isInitializedSubscribe()) {
            handlerBuilder.onEvent(TeamCreated.BeginJobDomainEvent.class, this::handleBeginTeamCreated, TeamCreated.BeginJobDomainEvent.TYPE)
                .onEvent(TeamAddedUsers.BeginJobDomainEvent.class, this::handleBeginTeamAddedUsers, TeamAddedUsers.BeginJobDomainEvent.TYPE);

        }

        if (applicationConfiguration.isProcessedSubscribe()) {
            handlerBuilder.onEvent(TeamCreated.ProcessedJobDomainEvent.class, this::handleProcessedTeamCreated, TeamCreated.ProcessedJobDomainEvent.TYPE)
                .onEvent(TeamAddedUsers.ProcessedJobDomainEvent.class, this::handleProcessedTeamAddedUsers, TeamAddedUsers.ProcessedJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isFailedSubscribe()) {
            handlerBuilder.onEvent(TeamCreated.FailedJobDomainEvent.class, this::handleFailedTeamCreated, TeamCreated.FailedJobDomainEvent.TYPE)
                .onEvent(TeamAddedUsers.FailedJobDomainEvent.class, this::handleFailedTeamAddedUsers, TeamAddedUsers.FailedJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isSuccessSubscribe()) {
            handlerBuilder.onEvent(TeamCreated.SuccessJobDomainEvent.class, this::handleSuccessfullyTeamCreated, TeamCreated.SuccessJobDomainEvent.TYPE)
                .onEvent(TeamAddedUsers.SuccessJobDomainEvent.class, this::handleSuccessfullyTeamAddedUsers, TeamAddedUsers.SuccessJobDomainEvent.TYPE);
        }

        return handlerBuilder.build();
    }

    private void handleBeginTeamCreated(DomainEventEnvelope<TeamCreated.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TeamCreated.BeginJobDomainEvent.TYPE);
    }

    private void handleProcessedTeamCreated(DomainEventEnvelope<TeamCreated.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TeamCreated.ProcessedJobDomainEvent.TYPE);
    }

    private void handleFailedTeamCreated(DomainEventEnvelope<TeamCreated.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TeamCreated.FailedJobDomainEvent.TYPE);
    }

    private void handleSuccessfullyTeamCreated(DomainEventEnvelope<TeamCreated.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TeamCreated.SuccessJobDomainEvent.TYPE);
    }

    private void handleBeginTeamAddedUsers(DomainEventEnvelope<TeamAddedUsers.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TeamAddedUsers.BeginJobDomainEvent.TYPE);
    }

    private void handleProcessedTeamAddedUsers(DomainEventEnvelope<TeamAddedUsers.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TeamAddedUsers.ProcessedJobDomainEvent.TYPE);
    }

    private void handleFailedTeamAddedUsers(DomainEventEnvelope<TeamAddedUsers.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TeamAddedUsers.FailedJobDomainEvent.TYPE);
    }

    private void handleSuccessfullyTeamAddedUsers(DomainEventEnvelope<TeamAddedUsers.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TeamAddedUsers.SuccessJobDomainEvent.TYPE);
    }
}
