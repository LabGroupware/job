package org.cresplanex.nova.job.exception;

import build.buf.gen.job.v1.JobServiceErrorCode;
import build.buf.gen.job.v1.JobServiceErrorMeta;
import build.buf.gen.job.v1.JobServiceInternalError;
import build.buf.gen.userprofile.v1.*;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@Slf4j
@GrpcAdvice
public class GrpcExceptionAdvice {

    @GrpcExceptionHandler
    public Status handleInternal(Throwable e) {
        log.error("Internal error", e);

        String message = e.getMessage() != null ? e.getMessage() : "Unknown error occurred";

         JobServiceInternalError.Builder descriptionBuilder =
                 JobServiceInternalError.newBuilder()
                         .setMeta(JobServiceErrorMeta.newBuilder()
                                 .setCode(JobServiceErrorCode.JOB_SERVICE_ERROR_CODE_INTERNAL)
                                 .setMessage(message)
                                 .build());

         return Status.INTERNAL
                 .withDescription(descriptionBuilder.build().toString())
                 .withCause(e);
    }
}
