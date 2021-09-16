package com.github.ricardobaumann.jobservice

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class JobExecutionService(
    private val jobService: JobService,
    private val jobExecutionRepo: JobExecutionRepo
) {

    companion object {
        private val log = LoggerFactory.getLogger(JobExecutionService::class.java)
    }

    fun triggerExecutionFor(jobEntity: JobEntity) =

        //trigger execution on target, and collect result
        jobExecutionRepo.save(
            JobExecutionEntity(
                id = UUID.randomUUID().toString(),
                startedAt = LocalDateTime.now(),
                executionStatus = ExecutionStatus.SCHEDULED,
                jobEntity = jobEntity
            )
        ).let {
            jobEntity.lastStatus = it.executionStatus
            jobService.updateExecution(jobEntity)
        }

    fun updateExecution(id: String, updateExecutionCommand: UpdateExecutionCommand) =
        jobExecutionRepo.findByIdOrNull(id)
            ?.let {
                jobExecutionRepo.save(it.apply {
                    this.responsePayload = updateExecutionCommand.responsePayload
                    this.finishedAt = LocalDateTime.now()
                    this.executionStatus = updateExecutionCommand.executionStatus
                })
            }?.also { jobExecutionEntity ->
                jobService.updateExecution(jobExecutionEntity.jobEntity.apply {
                    this.lastStatus = jobExecutionEntity.executionStatus
                })
            }

}