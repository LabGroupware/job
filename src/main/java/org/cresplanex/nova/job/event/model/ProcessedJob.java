package org.cresplanex.nova.job.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProcessedJob {

    private String jobId;
    private Object data;
    private String actionCode;
    private String internalCode;
    private String internalCaption;
    private String timestamp;

    abstract public String getType();
}
