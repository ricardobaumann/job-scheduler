package com.github.ricardobaumann.jobservice.services

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class JobScheduler(
    private val jobService: JobService,
    private val jobExecutionService: JobExecutionService,
    private val cronService: CronService
) {

    companion object {
        private val log = LoggerFactory.getLogger(JobScheduler::class.java)
    }

    //@Scheduled(cron = "*/1 * * * * *")
    fun poll() {
        val jobs = jobService.findAll()
        val truncatedNow = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        log.info("Checking time table {}", truncatedNow)
        jobs.filter {
            val nextExecution = cronService.getNextExecutionFor(it.cronString, truncatedNow)
                .truncatedTo(ChronoUnit.SECONDS)
            log.info(
                "Checking: {} against {}",
                nextExecution,
                truncatedNow
            )
            nextExecution
                .isEqual(truncatedNow)
        }.forEach {
            log.info("Executing {}", it)
            jobExecutionService.triggerExecutionFor(it)
        }
    }

}