package org.cresplanex.nova.job.event.model.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobBegan implements JobDomainEvent {
    public static final String TYPE = "org.cresplanex.nova.job.event.Job.Begin";

    private String jobId;
    private String actionCode;
    private String timestamp;
}
