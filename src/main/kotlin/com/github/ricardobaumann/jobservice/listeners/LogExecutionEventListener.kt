package com.github.ricardobaumann.jobservice.listeners

import com.github.ricardobaumann.jobservice.domain.JobExecutionEntity
import com.github.ricardobaumann.jobservice.services.ExecutionLogService
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class LogExecutionEventListener(private val executionLogService: ExecutionLogService) {

    companion object {
        private val log = LoggerFactory.getLogger(LogExecutionEventListener::class.java)
    }

    @EventListener
    fun handle(jobExecutionEntity: JobExecutionEntity) {
        log.info("Listening for log event: {}", jobExecutionEntity)
        executionLogService.logFor(jobExecutionEntity)
    }

}