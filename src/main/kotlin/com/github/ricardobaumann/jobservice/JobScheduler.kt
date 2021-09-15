package com.github.ricardobaumann.jobservice

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class JobScheduler(private val jobService: JobService) {

    companion object {
        private val log = LoggerFactory.getLogger(JobScheduler::class.java)
    }

    @Scheduled(cron = "*/1 * * * * *")
    fun poll() {
        log.info("Checking time table")
        jobService.findAll()
            .filter {
                val now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
                log.info("Checking if ${it.nextExecution} is $now")
                val nextExecution = it.nextExecution.truncatedTo(ChronoUnit.SECONDS)
                nextExecution
                    .isEqual(now)
            }.forEach {

                //execute job
                it.lastStatus = JobStatus.SUCCESS
                log.info("Executing $it")

                jobService.updateExecution(it)
            }
    }

}