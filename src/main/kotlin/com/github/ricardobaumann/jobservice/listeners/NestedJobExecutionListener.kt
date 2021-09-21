package com.github.ricardobaumann.jobservice.listeners

import com.github.ricardobaumann.jobservice.entities.JobExecutionEntity
import com.github.ricardobaumann.jobservice.services.NestedJobScheduleService
import com.github.ricardobaumann.jobservice.services.TriggerNestedJobCommand
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NestedJobExecutionListener(private val nestedJobScheduleService: NestedJobScheduleService) {

    companion object {
        private val log = LoggerFactory.getLogger(NestedJobExecutionListener::class.java)
    }

    @Async
    @EventListener
    fun handle(jobExecutionEntity: JobExecutionEntity) {
        log.info("Checking for nested jobs to be triggered for job {}", jobExecutionEntity.jobEntity)
        nestedJobScheduleService.checkAndTriggerFrom(
            TriggerNestedJobCommand(
                jobEntity = jobExecutionEntity.jobEntity,
                executionStatus = jobExecutionEntity.executionStatus
            )
        )
    }
}