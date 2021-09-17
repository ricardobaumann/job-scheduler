package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.ExecutionCommand
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
    private val jobExecutionRepo: JobExecutionRepo,
    private val commandExecutionService: CommandExecutionService
) {

    fun triggerExecutionFor(jobEntity: JobEntity): JobExecutionEntity {

        //trigger execution on target, and collect result
        val executionId = UUID.randomUUID().toString()
        val executionResult = commandExecutionService.execute(
            ExecutionCommand(
                executionId = executionId,
                commandType = jobEntity.commandType,
                command = jobEntity.command
            )
        )

        val jobExecutionEntity = jobExecutionRepo.save(
            JobExecutionEntity(
                id = executionId,
                startedAt = LocalDateTime.now(),
                executionStatus = executionResult.executionStatus,
                responsePayload = executionResult.responsePayload.toString(),
                jobEntity = jobEntity
            )
        )
        jobService.updateExecution(jobEntity.apply {
            this.lastStatus = jobExecutionEntity.executionStatus
        })

        return jobExecutionEntity
    }

    fun updateExecution(id: String, updateExecutionCommand: UpdateExecutionCommand) =
        jobExecutionRepo.findByIdOrNull(id)
            ?.let {
                jobExecutionRepo.save(it.apply {
                    this.responsePayload = updateExecutionCommand.responsePayload?.toString()
                    this.finishedAt = LocalDateTime.now()
                    this.executionStatus = updateExecutionCommand.executionStatus
                })
            }?.also { jobExecutionEntity ->
                jobService.updateExecution(jobExecutionEntity.jobEntity.apply {
                    this.lastStatus = jobExecutionEntity.executionStatus
                })
            }

}