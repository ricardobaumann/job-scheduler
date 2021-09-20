package com.github.ricardobaumann.jobservice.listeners

import com.github.ricardobaumann.jobservice.domain.JobExecutionEntity
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NestedJobExecutionListener {

    companion object {
        private val log = LoggerFactory.getLogger(NestedJobExecutionListener::class.java)
    }

    @EventListener
    fun handle(jobExecutionEntity: JobExecutionEntity) {
        log.info("Just logging for now")
    }
}