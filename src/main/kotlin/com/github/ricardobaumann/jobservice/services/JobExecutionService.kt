package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.ExecutionCommand
import com.github.ricardobaumann.jobservice.domain.UpdateExecutionCommand
import com.github.ricardobaumann.jobservice.entities.JobEntity
import com.github.ricardobaumann.jobservice.entities.JobExecutionEntity
import com.github.ricardobaumann.jobservice.repos.JobExecutionRepo
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class JobExecutionService(
    private val jobExecutionRepo: JobExecutionRepo,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val commandExecutionService: CommandExecutionService
) {

    fun triggerExecutionFor(jobEntity: JobEntity): JobExecutionEntity {

        val executionId = UUID.randomUUID().toString()
        val startedAt = LocalDateTime.now()
        val executionResult = commandExecutionService.execute(
            ExecutionCommand(
                executionId = executionId,
                commandType = jobEntity.commandType,
                command = jobEntity.command
            )
        )

        return save(
            JobExecutionEntity(
                id = executionId,
                startedAt = startedAt,
                executionStatus = executionResult.executionStatus,
                responsePayload = executionResult.responsePayload.toString(),
                jobEntity = jobEntity
            )
        )
    }

    fun updateExecution(id: String, updateExecutionCommand: UpdateExecutionCommand) =
        jobExecutionRepo.findByIdOrNull(id)
            ?.let { jobExecutionEntity ->
                save(jobExecutionEntity.apply {
                    this.responsePayload = updateExecutionCommand.responsePayload?.toString()
                    this.executionStatus = updateExecutionCommand.executionStatus
                })
            }

    private fun save(jobExecutionEntity: JobExecutionEntity) =
        jobExecutionRepo.save(jobExecutionEntity)
            .also {
                applicationEventPublisher.publishEvent(it)
            }

}
