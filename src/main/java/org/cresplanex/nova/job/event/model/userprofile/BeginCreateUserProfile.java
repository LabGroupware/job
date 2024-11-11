package org.cresplanex.nova.job.event.model.userprofile;

import org.cresplanex.nova.job.event.model.BeginJob;

import java.util.List;

public class BeginCreateUserProfile extends BeginJob implements UserProfileDomainEvent{
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.event.UserProfile.CreateBegin";

    public BeginCreateUserProfile(String jobId, List<String> toActionCodes, String pendingActionCode, String timestamp) {
        super(jobId, toActionCodes, pendingActionCode, timestamp);
    }

    public BeginCreateUserProfile() {
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
