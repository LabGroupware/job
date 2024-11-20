package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.organization.OrganizationAddedUsers;
import org.cresplanex.api.state.common.event.model.organization.OrganizationCreated;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class OrganizationEventHandler {

    private final JobService jobService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(EventAggregateType.ORGANIZATION)
                .onEvent(OrganizationCreated.BeginJobDomainEvent.class, this::handleBeginOrganizationCreated, OrganizationCreated.BeginJobDomainEvent.TYPE)
                .onEvent(OrganizationCreated.ProcessedJobDomainEvent.class, this::handleProcessedOrganizationCreated, OrganizationCreated.ProcessedJobDomainEvent.TYPE)
                .onEvent(OrganizationCreated.FailedJobDomainEvent.class, this::handleFailedOrganizationCreated, OrganizationCreated.FailedJobDomainEvent.TYPE)
                .onEvent(OrganizationCreated.SuccessJobDomainEvent.class, this::handleSuccessfullyOrganizationCreated, OrganizationCreated.SuccessJobDomainEvent.TYPE)

                .onEvent(OrganizationAddedUsers.BeginJobDomainEvent.class, this::handleBeginOrganizationAddedUsers, OrganizationAddedUsers.BeginJobDomainEvent.TYPE)
                .onEvent(OrganizationAddedUsers.ProcessedJobDomainEvent.class, this::handleProcessedOrganizationAddedUsers, OrganizationAddedUsers.ProcessedJobDomainEvent.TYPE)
                .onEvent(OrganizationAddedUsers.FailedJobDomainEvent.class, this::handleFailedOrganizationAddedUsers, OrganizationAddedUsers.FailedJobDomainEvent.TYPE)
                .onEvent(OrganizationAddedUsers.SuccessJobDomainEvent.class, this::handleSuccessfullyOrganizationAddedUsers, OrganizationAddedUsers.SuccessJobDomainEvent.TYPE)
                .build();
    }

    private void handleBeginOrganizationCreated(DomainEventEnvelope<OrganizationCreated.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleProcessedOrganizationCreated(DomainEventEnvelope<OrganizationCreated.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleFailedOrganizationCreated(DomainEventEnvelope<OrganizationCreated.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleSuccessfullyOrganizationCreated(DomainEventEnvelope<OrganizationCreated.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleBeginOrganizationAddedUsers(DomainEventEnvelope<OrganizationAddedUsers.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleProcessedOrganizationAddedUsers(DomainEventEnvelope<OrganizationAddedUsers.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleFailedOrganizationAddedUsers(DomainEventEnvelope<OrganizationAddedUsers.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleSuccessfullyOrganizationAddedUsers(DomainEventEnvelope<OrganizationAddedUsers.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }
}
