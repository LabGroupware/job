package org.cresplanex.nova.job.event.model.userprofile;

import org.cresplanex.nova.job.event.model.SuccessfullyJob;

public class SuccessfullyCreateUserProfile extends SuccessfullyJob implements UserProfileDomainEvent {
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.event.UserProfile.CreateSuccessfully";

    public SuccessfullyCreateUserProfile(
            String jobId,
            Object data
    ) {
        super(jobId, data);
    }

    public SuccessfullyCreateUserProfile() {
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
