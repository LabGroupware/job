package org.cresplanex.nova.job.event.publisher;

import build.buf.gen.job.v1.Job;
import org.cresplanex.api.state.common.event.EventAggregateType;
import org.cresplanex.api.state.common.event.model.job.JobDomainEvent;
import org.cresplanex.core.events.aggregates.AbstractAggregateDomainEventPublisher;
import org.cresplanex.core.events.publisher.DomainEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class JobDomainEventPublisher extends AbstractAggregateDomainEventPublisher<Job, JobDomainEvent> {

    public JobDomainEventPublisher(DomainEventPublisher eventPublisher) {
        super(eventPublisher, Job.class, Job::getJobId, EventAggregateType.JOB);
    }
}
