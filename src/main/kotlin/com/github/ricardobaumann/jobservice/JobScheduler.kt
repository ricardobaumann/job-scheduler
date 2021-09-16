package com.github.ricardobaumann.jobservice

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class JobScheduler(
    private val jobService: JobService,
    private val jobExecutionService: JobExecutionService
) {

    companion object {
        private val log = LoggerFactory.getLogger(JobScheduler::class.java)
    }

    @Scheduled(cron = "*/1 * * * * *")
    fun poll() {
        val truncatedNow = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        log.info("Checking time table")
        jobService.findAll()
            .filter {
                it.nextExecution.truncatedTo(ChronoUnit.SECONDS)
                    .isEqual(truncatedNow)
            }.forEach {
                jobExecutionService.triggerExecutionFor(it)
            }
    }

}