package org.cresplanex.nova.job.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class SuccessfullyJob {

    private String jobId;
    private Object endedData;

    abstract public String getType();
}
