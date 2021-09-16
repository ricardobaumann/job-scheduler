package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import com.github.ricardobaumann.jobservice.domain.JobEntity
import com.github.ricardobaumann.jobservice.domain.JobExecutionEntity
import com.github.ricardobaumann.jobservice.domain.UpdateExecutionCommand
import com.github.ricardobaumann.jobservice.repos.JobExecutionRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class JobExecutionService(
    private val jobService: JobService,
    private val jobExecutionRepo: JobExecutionRepo
) {

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
            jobService.updateExecution(jobEntity.apply {
                this.lastStatus = it.executionStatus
            })
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