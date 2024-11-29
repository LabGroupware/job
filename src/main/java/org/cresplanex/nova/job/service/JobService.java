package org.cresplanex.nova.job.service;

import build.buf.gen.cresplanex.nova.v1.NullableFlex;
import build.buf.gen.cresplanex.nova.v1.NullableString;
import build.buf.gen.cresplanex.nova.v1.NullableStringArray;
import build.buf.gen.job.v1.Job;
import build.buf.gen.job.v1.JobAction;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cresplanex.api.state.common.constants.JobServiceApplicationCode;
import org.cresplanex.api.state.common.dto.JobDto;
import org.cresplanex.api.state.common.event.model.BeginJobEvent;
import org.cresplanex.api.state.common.event.model.FailedJobEvent;
import org.cresplanex.api.state.common.event.model.ProcessedJobEvent;
import org.cresplanex.api.state.common.event.model.SuccessJobEvent;
import org.cresplanex.api.state.common.event.model.job.JobBegan;
import org.cresplanex.api.state.common.event.model.job.JobFailed;
import org.cresplanex.api.state.common.event.model.job.JobProcessed;
import org.cresplanex.api.state.common.event.model.job.JobSuccess;
import org.cresplanex.api.state.common.utils.CustomIdGenerator;
import org.cresplanex.api.state.common.utils.NullableFlexProtoMapper;
import org.cresplanex.nova.job.constants.KeyPrefix;
import org.cresplanex.nova.job.event.publisher.JobDomainEventPublisher;
import org.cresplanex.nova.job.mapper.JobMapper;
import org.cresplanex.nova.job.template.KeyValueTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {

    @Value("${job.expired-time:86400000}")
    private long expiredTime;

    private final CustomIdGenerator customIdGenerator = new CustomIdGenerator();
    private final KeyValueTemplate keyValueTemplate;

    private final JobDomainEventPublisher domainEventPublisher;

    public String create() {
        String id = customIdGenerator.generate();
        return create(id);
    }

    public String create(String id) {
        Job job = Job.newBuilder()
                .setJobId(id)
                .setInitialized(false)
                .setSuccess(false)
                .setProcess(false)
                .setIsValid(true)
                .setData(NullableFlex.newBuilder().setHasValue(false).build())
                .setScheduledActions(NullableStringArray.newBuilder().setHasValue(false).build())
                .setPendingAction(NullableString.newBuilder().setHasValue(false).build())
                .setCode(NullableString.newBuilder()
                        .setHasValue(true)
                        .setValue(JobServiceApplicationCode.NOT_INITIALIZED)
                        .build()
                )
                .setCaption(
                        NullableString.newBuilder()
                                .setHasValue(true)
                                .setValue("Job not initialized.")
                                .build()
                )
                .setErrorAttributes(NullableFlex.newBuilder().setHasValue(false).build())
                .setStartedAt(NullableString.newBuilder().setHasValue(false).build())
                .setExpiredAt(NullableString.newBuilder().setHasValue(false).build())
                .setCompletedAt(NullableString.newBuilder().setHasValue(false).build())
                .build();

        keyValueTemplate.setValue(getKey(id), job.toByteArray());
        return id;
    }

    public Job findById(String id) {
        Optional<byte[]> job = keyValueTemplate.getValue(getKey(id));
        if (job.isPresent()) {
            try {
                return Job.parseFrom(job.get());
            } catch (InvalidProtocolBufferException e) {
                throw new RuntimeException(e);
            }
        } else {
            return buildNotFoundJob(id);
        }
    }

    public void update(BeginJobEvent beginJob, String type) {
        Job job = findById(beginJob.getJobId());

        String startTimeStr = beginJob.getTimestamp();
        LocalDateTime startLocalTime = LocalDateTime.parse(startTimeStr);
        LocalDateTime expiredLocalTime = startLocalTime.plusSeconds(this.expiredTime/1000);
        Job updatedJob = Job.newBuilder(job)
                .setInitialized(true)
                .setSuccess(false)
                .setProcess(true)
                .setIsValid(true)
                .setScheduledActions(
                        NullableStringArray.newBuilder()
                                .setHasValue(true)
                                .addAllValue(beginJob.getToActionCodes())
                                .build()
                )
                .setPendingAction(
                        NullableString.newBuilder()
                                .setHasValue(true)
                                .setValue(beginJob.getPendingActionCode())
                                .build()
                )
                .setCode(NullableString.newBuilder()
                        .setHasValue(true)
                        .setValue(JobServiceApplicationCode.PROCESSING)
                        .build()
                )
                .setCaption(
                        NullableString.newBuilder()
                                .setHasValue(true)
                                .setValue("Job processing.")
                                .build()
                )
                .setStartedAt(NullableString.newBuilder()
                        .setHasValue(true)
                        .setValue(startLocalTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .build()
                )
                .setExpiredAt(NullableString.newBuilder()
                        .setHasValue(true)
                        .setValue(expiredLocalTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .build()
                )
                .build();
        keyValueTemplate.setValue(
                getKey(beginJob.getJobId()),
                updatedJob.toByteArray(),
                expiredTime,
                TimeUnit.MILLISECONDS
        );

        domainEventPublisher.publish(
                updatedJob,
                Collections.singletonList(
                        new JobBegan(
                                beginJob.getJobId(),
                                type,
                                beginJob.getPendingActionCode(),
                                beginJob.getToActionCodes(),
                                beginJob.getTimestamp()
                        )
                ),
                JobBegan.TYPE
        );
    }

    public void update(ProcessedJobEvent processedJob, String type) {
        Job job = findById(processedJob.getJobId());
        log.debug("Before Job: {}", job);
        List<String> scheduledActions = job.getScheduledActions().getValueList();
        NullableString.Builder pendingAction = NullableString.newBuilder();
        if (scheduledActions.isEmpty()) { // 次のアクションがある場合
            pendingAction.setHasValue(false);
        }else{
            pendingAction.setHasValue(true).setValue(scheduledActions.getFirst());
            scheduledActions = scheduledActions.subList(1, scheduledActions.size());
        }
        NullableFlex data = NullableFlexProtoMapper.mapToNullableFlex(processedJob.getData());
        JobAction newAction = JobAction.newBuilder()
                .setActionCode(processedJob.getActionCode())
                .setSuccess(true)
                .setData(data)
                .setCode(processedJob.getInternalCode())
                .setCaption(processedJob.getInternalCaption())
                .setErrorAttributes(
                        NullableFlex.newBuilder()
                                .setHasValue(false)
                                .build()
                )
                .setDatetime(processedJob.getTimestamp())
                .build();
        Job updatedJob = Job.newBuilder(job)
                .setSuccess(false)
                .setProcess(true)
                .setIsValid(true)
                .setScheduledActions(
                        NullableStringArray.newBuilder()
                                .setHasValue(true)
                                .addAllValue(scheduledActions)
                                .build()
                )
                .setPendingAction(pendingAction.build())
                .addCompletedActions(newAction)
                .setErrorAttributes(NullableFlex.newBuilder().setHasValue(false).build())
                .build();
        log.debug("After Job: {}", updatedJob);
        updateOnlyValue(updatedJob, processedJob.getJobId());

        JobDto dto = JobMapper.convert(updatedJob);

        domainEventPublisher.publish(
                updatedJob,
                Collections.singletonList(
                        new JobProcessed(
                                processedJob.getJobId(),
                                type,
                                dto.getPendingAction(),
                                dto.getCompletedActions(),
                                dto.getScheduledActions(),
                                processedJob.getTimestamp()
                        )
                ),
                JobProcessed.TYPE
        );
    }

    public void update(SuccessJobEvent successfullyJob, String type) {
        Job job = findById(successfullyJob.getJobId());
        Job updatedJob = Job.newBuilder(job)
                .setSuccess(true)
                .setProcess(false)
                .setIsValid(true)
                .setData(
                    NullableFlexProtoMapper.mapToNullableFlex(successfullyJob.getEndedData())
                )
                .setCode(NullableString.newBuilder()
                        .setHasValue(true)
                        .setValue(JobServiceApplicationCode.COMPLETED)
                        .build()
                )
                .setCaption(
                        NullableString.newBuilder()
                                .setHasValue(true)
                                .setValue("Job completed successfully.")
                                .build()
                )
                .setCompletedAt(NullableString.newBuilder()
                        .setHasValue(true)
                        .setValue(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .build()
                )
                .build();
        log.debug("Successfully Job: {}", updatedJob);
        log.info("jobId: {}", successfullyJob.getJobId());
        updateOnlyValue(updatedJob, successfullyJob.getJobId());

        JobDto dto = JobMapper.convert(updatedJob);

        domainEventPublisher.publish(
                updatedJob,
                Collections.singletonList(
                        new JobSuccess(
                                successfullyJob.getJobId(),
                                type,
                                dto.getCompletedActions(),
                                dto.getData(),
                                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        )
                ),
                JobSuccess.TYPE
        );
    }

    public void update(FailedJobEvent failedJob, String type) {
        Job job = findById(failedJob.getJobId());

        JobAction newAction = JobAction.newBuilder()
                .setActionCode(failedJob.getActionCode())
                .setSuccess(false)
                .setData(NullableFlex.newBuilder().setHasValue(false).build())
                .setCode(failedJob.getInternalCode())
                .setCaption(failedJob.getInternalCaption())
                .setErrorAttributes(NullableFlexProtoMapper.mapToNullableFlex(failedJob.getEndedErrorAttributes()))
                .setDatetime(failedJob.getTimestamp())
                .build();
        Job updatedJob = Job.newBuilder(job)
                .setSuccess(false)
                .setProcess(false)
                .setIsValid(true)
                .addCompletedActions(newAction)
                .setScheduledActions(NullableStringArray.newBuilder().setHasValue(false).build())
                .setPendingAction(NullableString.newBuilder().setHasValue(false).build())
                .setErrorAttributes(NullableFlexProtoMapper.mapToNullableFlex(failedJob.getEndedErrorAttributes()))
                .setCode(NullableString.newBuilder()
                        .setHasValue(true)
                        .setValue(JobServiceApplicationCode.FAILED)
                        .build()
                )
                .setCaption(
                        NullableString.newBuilder()
                                .setHasValue(true)
                                .setValue("Job failed.")
                                .build()
                )
                .setCompletedAt(NullableString.newBuilder()
                        .setHasValue(true)
                        .setValue(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                        .build()
                )
                .build();
        log.debug("Failed Job: {}", updatedJob);
        log.info("jobId: {}", failedJob.getJobId());
        updateOnlyValue(updatedJob, failedJob.getJobId());

        JobDto dto = JobMapper.convert(updatedJob);

        domainEventPublisher.publish(
                updatedJob,
                Collections.singletonList(
                        new JobFailed(
                                failedJob.getJobId(),
                                type,
                                dto.getCompletedActions(),
                                dto.getErrorAttributes(),
                                failedJob.getTimestamp()
                        )
                ),
                JobFailed.TYPE
        );
    }

    private void updateOnlyValue(Job updatedJob, String jobId) {
        Long currentTtl = keyValueTemplate.getExpire(getKey(jobId), TimeUnit.MILLISECONDS);
        if (currentTtl != null && currentTtl > 0) {
            keyValueTemplate.setValue(
                    getKey(jobId),
                    updatedJob.toByteArray(),
                    currentTtl,
                    TimeUnit.MILLISECONDS
            );
        }else{
            // 期限が設定されていない場合は、デフォルトの期限を設定
            keyValueTemplate.setValue(
                    getKey(jobId),
                    updatedJob.toByteArray(),
                    expiredTime,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    private Job buildNotFoundJob(String id) {
        return Job.newBuilder()
                .setJobId(id)
                .setInitialized(false)
                .setSuccess(false)
                .setProcess(false)
                .setIsValid(false)
                .setData(NullableFlex.newBuilder().setHasValue(false).build())
                .setScheduledActions(NullableStringArray.newBuilder().setHasValue(false).build())
                .setPendingAction(NullableString.newBuilder().setHasValue(false).build())
                .setCode(
                        NullableString.newBuilder()
                                .setHasValue(true)
                                .setValue(JobServiceApplicationCode.NOT_FOUND)
                                .build()
                )
                .setCaption(
                        NullableString.newBuilder()
                                .setHasValue(true)
                                .setValue("Job not found.")
                                .build()
                )
                .setErrorAttributes(NullableFlex.newBuilder().setHasValue(false).build())
                .setStartedAt(NullableString.newBuilder().setHasValue(false).build())
                .setExpiredAt(NullableString.newBuilder().setHasValue(false).build())
                .setCompletedAt(NullableString.newBuilder().setHasValue(false).build())
                .build();
    }

    private String getKey(String id) {
        return KeyPrefix.JOB + ":" + id;
    }
}
