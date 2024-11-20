package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.team.TeamAddedUsers;
import org.cresplanex.api.state.common.event.model.team.TeamCreated;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class TeamEventHandler {

    private final JobService jobService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(EventAggregateType.TEAM)
                .onEvent(TeamCreated.BeginJobDomainEvent.class, this::handleBeginTeamCreated, TeamCreated.BeginJobDomainEvent.TYPE)
                .onEvent(TeamCreated.ProcessedJobDomainEvent.class, this::handleProcessedTeamCreated, TeamCreated.ProcessedJobDomainEvent.TYPE)
                .onEvent(TeamCreated.FailedJobDomainEvent.class, this::handleFailedTeamCreated, TeamCreated.FailedJobDomainEvent.TYPE)
                .onEvent(TeamCreated.SuccessJobDomainEvent.class, this::handleSuccessfullyTeamCreated, TeamCreated.SuccessJobDomainEvent.TYPE)

                .onEvent(TeamAddedUsers.BeginJobDomainEvent.class, this::handleBeginTeamAddedUsers, TeamAddedUsers.BeginJobDomainEvent.TYPE)
                .onEvent(TeamAddedUsers.ProcessedJobDomainEvent.class, this::handleProcessedTeamAddedUsers, TeamAddedUsers.ProcessedJobDomainEvent.TYPE)
                .onEvent(TeamAddedUsers.FailedJobDomainEvent.class, this::handleFailedTeamAddedUsers, TeamAddedUsers.FailedJobDomainEvent.TYPE)
                .onEvent(TeamAddedUsers.SuccessJobDomainEvent.class, this::handleSuccessfullyTeamAddedUsers, TeamAddedUsers.SuccessJobDomainEvent.TYPE)
                .build();
    }

    private void handleBeginTeamCreated(DomainEventEnvelope<TeamCreated.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleProcessedTeamCreated(DomainEventEnvelope<TeamCreated.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleFailedTeamCreated(DomainEventEnvelope<TeamCreated.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleSuccessfullyTeamCreated(DomainEventEnvelope<TeamCreated.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleBeginTeamAddedUsers(DomainEventEnvelope<TeamAddedUsers.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleProcessedTeamAddedUsers(DomainEventEnvelope<TeamAddedUsers.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleFailedTeamAddedUsers(DomainEventEnvelope<TeamAddedUsers.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleSuccessfullyTeamAddedUsers(DomainEventEnvelope<TeamAddedUsers.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }
}
