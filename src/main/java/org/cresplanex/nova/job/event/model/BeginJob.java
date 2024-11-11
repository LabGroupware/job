package org.cresplanex.nova.job.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BeginJob {

    private String jobId;
    private List<String> toActionCodes;
    private String pendingActionCode;
    private String timestamp;

    abstract public String getType();
}
