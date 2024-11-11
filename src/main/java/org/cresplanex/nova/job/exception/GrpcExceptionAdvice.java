package org.cresplanex.nova.job.exception;

import build.buf.gen.job.v1.JobServiceErrorCode;
import build.buf.gen.job.v1.JobServiceErrorMeta;
import build.buf.gen.job.v1.JobServiceInternalError;
import build.buf.gen.userprofile.v1.*;
import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcExceptionAdvice {

    @GrpcExceptionHandler
    public Status handleInternal(Throwable e) {
         JobServiceInternalError.Builder descriptionBuilder =
                 JobServiceInternalError.newBuilder()
                         .setMeta(JobServiceErrorMeta.newBuilder()
                                 .setCode(JobServiceErrorCode.JOB_SERVICE_ERROR_CODE_INTERNAL)
                                 .setMessage(e.getMessage())
                                 .build());

         return Status.INTERNAL
                 .withDescription(descriptionBuilder.build().toString())
                 .withCause(e);
    }
}
