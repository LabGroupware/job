package org.cresplanex.nova.job.event.model.userprofile;

import org.cresplanex.nova.job.event.model.ProcessedJob;

public class ProcessedCreateUserProfile extends ProcessedJob implements UserProfileDomainEvent {
    public final static String TYPE = "org.cresplanex.nova.service.userprofile.event.UserProfile.CreateProcessed";

    public ProcessedCreateUserProfile(
            String jobId,
            Object data,
            String actionCode,
            String internalCode,
            String internalCaption,
            String timestamp
    ) {
        super(jobId, data, actionCode, internalCode, internalCaption, timestamp);
    }

    public ProcessedCreateUserProfile() {
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
