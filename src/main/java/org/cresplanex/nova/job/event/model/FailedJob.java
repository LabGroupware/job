package org.cresplanex.nova.job.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class FailedJob {

    private String jobId;
    private Object data;
    private String actionCode;
    private String internalCode;
    private String internalCaption;
    private String timestamp;
    private Object endedErrorAttributes;

    abstract public String getType();
}
