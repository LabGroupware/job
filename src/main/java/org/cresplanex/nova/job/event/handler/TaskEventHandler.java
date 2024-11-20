package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.plan.TaskCreated;
import org.cresplanex.api.state.common.event.model.plan.TaskUpdatedStatus;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class TaskEventHandler {

    private final JobService jobService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(EventAggregateType.PLAN_TASK)
                .onEvent(TaskCreated.BeginJobDomainEvent.class, this::handleBeginTaskCreated, TaskCreated.BeginJobDomainEvent.TYPE)
                .onEvent(TaskCreated.ProcessedJobDomainEvent.class, this::handleProcessedTaskCreated, TaskCreated.ProcessedJobDomainEvent.TYPE)
                .onEvent(TaskCreated.FailedJobDomainEvent.class, this::handleFailedTaskCreated, TaskCreated.FailedJobDomainEvent.TYPE)
                .onEvent(TaskCreated.SuccessJobDomainEvent.class, this::handleSuccessfullyTaskCreated, TaskCreated.SuccessJobDomainEvent.TYPE)

                .onEvent(TaskUpdatedStatus.BeginJobDomainEvent.class, this::handleBeginTaskUpdatedStatus, TaskUpdatedStatus.BeginJobDomainEvent.TYPE)
                .onEvent(TaskUpdatedStatus.ProcessedJobDomainEvent.class, this::handleProcessedTaskUpdatedStatus, TaskUpdatedStatus.ProcessedJobDomainEvent.TYPE)
                .onEvent(TaskUpdatedStatus.FailedJobDomainEvent.class, this::handleFailedTaskUpdatedStatus, TaskUpdatedStatus.FailedJobDomainEvent.TYPE)
                .onEvent(TaskUpdatedStatus.SuccessJobDomainEvent.class, this::handleSuccessfullyTaskUpdatedStatus, TaskUpdatedStatus.SuccessJobDomainEvent.TYPE)
                .build();
    }

    private void handleBeginTaskCreated(DomainEventEnvelope<TaskCreated.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleProcessedTaskCreated(DomainEventEnvelope<TaskCreated.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleFailedTaskCreated(DomainEventEnvelope<TaskCreated.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleSuccessfullyTaskCreated(DomainEventEnvelope<TaskCreated.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleBeginTaskUpdatedStatus(DomainEventEnvelope<TaskUpdatedStatus.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleProcessedTaskUpdatedStatus(DomainEventEnvelope<TaskUpdatedStatus.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleFailedTaskUpdatedStatus(DomainEventEnvelope<TaskUpdatedStatus.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }

    private void handleSuccessfullyTaskUpdatedStatus(DomainEventEnvelope<TaskUpdatedStatus.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent());
    }
}
