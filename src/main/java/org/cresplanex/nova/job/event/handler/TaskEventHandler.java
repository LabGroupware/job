package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.plan.TaskCreated;
import org.cresplanex.api.state.common.event.model.plan.TaskUpdatedStatus;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.config.ApplicationConfiguration;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class TaskEventHandler {

    private final JobService jobService;

    private final ApplicationConfiguration applicationConfiguration;

    public DomainEventHandlers domainEventHandlers() {
        var handlerBuilder = DomainEventHandlersBuilder
                .forAggregateType(EventAggregateType.PLAN_TASK);

        if (applicationConfiguration.isInitializedSubscribe()) {
            handlerBuilder.onEvent(TaskCreated.BeginJobDomainEvent.class, this::handleBeginTaskCreated, TaskCreated.BeginJobDomainEvent.TYPE)
                .onEvent(TaskUpdatedStatus.BeginJobDomainEvent.class, this::handleBeginTaskUpdatedStatus, TaskUpdatedStatus.BeginJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isProcessedSubscribe()) {
            handlerBuilder.onEvent(TaskCreated.ProcessedJobDomainEvent.class, this::handleProcessedTaskCreated, TaskCreated.ProcessedJobDomainEvent.TYPE)
                .onEvent(TaskUpdatedStatus.ProcessedJobDomainEvent.class, this::handleProcessedTaskUpdatedStatus, TaskUpdatedStatus.ProcessedJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isFailedSubscribe()) {
            handlerBuilder.onEvent(TaskCreated.FailedJobDomainEvent.class, this::handleFailedTaskCreated, TaskCreated.FailedJobDomainEvent.TYPE)
                .onEvent(TaskUpdatedStatus.FailedJobDomainEvent.class, this::handleFailedTaskUpdatedStatus, TaskUpdatedStatus.FailedJobDomainEvent.TYPE);
        }

        if (applicationConfiguration.isSuccessSubscribe()) {
            handlerBuilder.onEvent(TaskCreated.SuccessJobDomainEvent.class, this::handleSuccessfullyTaskCreated, TaskCreated.SuccessJobDomainEvent.TYPE)
                .onEvent(TaskUpdatedStatus.SuccessJobDomainEvent.class, this::handleSuccessfullyTaskUpdatedStatus, TaskUpdatedStatus.SuccessJobDomainEvent.TYPE);
        }

        return handlerBuilder.build();
    }

    private void handleBeginTaskCreated(DomainEventEnvelope<TaskCreated.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TaskCreated.BeginJobDomainEvent.TYPE);
    }

    private void handleProcessedTaskCreated(DomainEventEnvelope<TaskCreated.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TaskCreated.ProcessedJobDomainEvent.TYPE);
    }

    private void handleFailedTaskCreated(DomainEventEnvelope<TaskCreated.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TaskCreated.FailedJobDomainEvent.TYPE);
    }

    private void handleSuccessfullyTaskCreated(DomainEventEnvelope<TaskCreated.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TaskCreated.SuccessJobDomainEvent.TYPE);
    }

    private void handleBeginTaskUpdatedStatus(DomainEventEnvelope<TaskUpdatedStatus.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TaskUpdatedStatus.BeginJobDomainEvent.TYPE);
    }

    private void handleProcessedTaskUpdatedStatus(DomainEventEnvelope<TaskUpdatedStatus.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TaskUpdatedStatus.ProcessedJobDomainEvent.TYPE);
    }

    private void handleFailedTaskUpdatedStatus(DomainEventEnvelope<TaskUpdatedStatus.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TaskUpdatedStatus.FailedJobDomainEvent.TYPE);
    }

    private void handleSuccessfullyTaskUpdatedStatus(DomainEventEnvelope<TaskUpdatedStatus.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), TaskUpdatedStatus.SuccessJobDomainEvent.TYPE);
    }
}
