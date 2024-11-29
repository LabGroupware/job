package org.cresplanex.nova.job.event.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.storage.FileObjectCreated;
import org.cresplanex.core.events.common.DomainEventEnvelope;
import org.cresplanex.core.events.subscriber.DomainEventHandlers;
import org.cresplanex.core.events.subscriber.DomainEventHandlersBuilder;
import org.cresplanex.nova.job.service.JobService;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class FileObjectEventHandler {

    private final JobService jobService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(EventAggregateType.STORAGE_OBJECT)
                .onEvent(FileObjectCreated.BeginJobDomainEvent.class, this::handleBeginFileObjectCreated, FileObjectCreated.BeginJobDomainEvent.TYPE)
                .onEvent(FileObjectCreated.ProcessedJobDomainEvent.class, this::handleProcessedFileObjectCreated, FileObjectCreated.ProcessedJobDomainEvent.TYPE)
                .onEvent(FileObjectCreated.FailedJobDomainEvent.class, this::handleFailedFileObjectCreated, FileObjectCreated.FailedJobDomainEvent.TYPE)
                .onEvent(FileObjectCreated.SuccessJobDomainEvent.class, this::handleSuccessfullyFileObjectCreated, FileObjectCreated.SuccessJobDomainEvent.TYPE)
                .build();
    }

    private void handleBeginFileObjectCreated(DomainEventEnvelope<FileObjectCreated.BeginJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), FileObjectCreated.BeginJobDomainEvent.TYPE);
    }

    private void handleProcessedFileObjectCreated(DomainEventEnvelope<FileObjectCreated.ProcessedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), FileObjectCreated.ProcessedJobDomainEvent.TYPE);
    }

    private void handleFailedFileObjectCreated(DomainEventEnvelope<FileObjectCreated.FailedJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), FileObjectCreated.FailedJobDomainEvent.TYPE);
    }

    private void handleSuccessfullyFileObjectCreated(DomainEventEnvelope<FileObjectCreated.SuccessJobDomainEvent> dee) {
        jobService.update(dee.getEvent(), FileObjectCreated.SuccessJobDomainEvent.TYPE);
    }
}
