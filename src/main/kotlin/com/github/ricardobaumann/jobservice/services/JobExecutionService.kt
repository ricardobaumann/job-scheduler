package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.*
import com.github.ricardobaumann.jobservice.repos.ExecutionLogRepo
import com.github.ricardobaumann.jobservice.repos.JobExecutionRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class JobExecutionService(
    private val jobExecutionRepo: JobExecutionRepo,
    private val executionLogRepo: ExecutionLogRepo,
    private val commandExecutionService: CommandExecutionService
) {

    fun triggerExecutionFor(jobEntity: JobEntity): JobExecutionEntity {

        val executionId = UUID.randomUUID().toString()
        val executionResult = commandExecutionService.execute(
            ExecutionCommand(
                executionId = executionId,
                commandType = jobEntity.commandType,
                command = jobEntity.command
            )
        )

        return jobExecutionRepo.save(
            JobExecutionEntity(
                id = executionId,
                startedAt = LocalDateTime.now(),
                executionStatus = executionResult.executionStatus,
                responsePayload = executionResult.responsePayload.toString(),
                jobEntity = jobEntity
            )
        )
    }

    fun updateExecution(id: String, updateExecutionCommand: UpdateExecutionCommand) =
        jobExecutionRepo.findByIdOrNull(id)
            ?.let { jobExecutionEntity ->
                jobExecutionRepo.save(jobExecutionEntity.apply {
                    this.responsePayload = updateExecutionCommand.responsePayload?.toString()
                    this.finishedAt = LocalDateTime.now()
                    this.executionStatus = updateExecutionCommand.executionStatus
                }).also {
                    executionLogRepo.save(
                        ExecutionLogEntity(
                            id = UUID.randomUUID().toString(),
                            jobExecutionEntity = it,
                            executionStatus = it.executionStatus,
                            responsePayload = it.responsePayload
                        )
                    )
                }
            }

}