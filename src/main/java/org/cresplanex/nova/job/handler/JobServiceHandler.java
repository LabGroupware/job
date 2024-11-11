package org.cresplanex.nova.job.handler;

import build.buf.gen.job.v1.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.cresplanex.nova.job.service.JobService;

@RequiredArgsConstructor
@GrpcService
public class JobServiceHandler extends JobServiceGrpc.JobServiceImplBase {

    private final JobService jobService;

    @Override
    public void createJob(CreateJobRequest request, StreamObserver<CreateJobResponse> responseObserver) {
        String jobId = jobService.create();
        CreateJobResponse response = CreateJobResponse.newBuilder()
                        .setJobId(jobId)
                        .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findJob(FindJobRequest request, StreamObserver<FindJobResponse> responseObserver) {
        Job job = jobService.findById(request.getJobId());
        FindJobResponse response = FindJobResponse.newBuilder()
                .setJob(job)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
