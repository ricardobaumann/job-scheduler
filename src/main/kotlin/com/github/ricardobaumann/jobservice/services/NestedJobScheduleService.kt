package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import com.github.ricardobaumann.jobservice.entities.JobEntity
import com.github.ricardobaumann.jobservice.listeners.NestedJobExecutionListener
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NestedJobScheduleService(
    private val triggerService: TriggerService,
    private val jobExecutionService: JobExecutionService
) {

    companion object {
        private val log = LoggerFactory.getLogger(NestedJobExecutionListener::class.java)
    }

    fun checkAndTriggerFrom(triggerNestedJobCommand: TriggerNestedJobCommand) {
        val jobEntity = triggerNestedJobCommand.jobEntity
        log.info("Loading jobs triggered by {}", jobEntity)
        triggerService.findTriggeredBy(jobEntity)
            .filter {
                it.executionStatus?.equals(triggerNestedJobCommand.executionStatus) ?: true
            }
            .forEach {
                val targetJob = it.targetJob
                log.info("Triggering {}", targetJob)
                jobExecutionService.triggerExecutionFor(targetJob)
            }
    }
}

data class TriggerNestedJobCommand(
    val jobEntity: JobEntity,
    val executionStatus: ExecutionStatus
)