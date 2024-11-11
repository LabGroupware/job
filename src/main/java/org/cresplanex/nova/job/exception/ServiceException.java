package org.cresplanex.nova.job.exception;

import build.buf.gen.job.v1.JobServiceErrorCode;

public abstract class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    abstract public JobServiceErrorCode getServiceErrorCode();
    abstract public String getErrorCaption();
}
