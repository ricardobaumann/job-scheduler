package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.JobEntity
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service
import java.util.concurrent.ScheduledFuture

@Service
class ScheduleService(
    private val taskScheduler: TaskScheduler,
    private val jobExecutionService: JobExecutionService
) {

    private val scheduledMap = mutableMapOf<String, ScheduledFuture<*>?>()

    fun schedule(jobEntity: JobEntity) {
        // we may cancel by building a map of job -> schedule result
        val future = taskScheduler.schedule({
            jobExecutionService.triggerExecutionFor(jobEntity)
        }, CronTrigger(jobEntity.cronString))
        scheduledMap[jobEntity.id] = future
    }

    fun cancel(jobEntity: JobEntity) {
        scheduledMap[jobEntity.id]?.also {
            it.cancel(false)
        }
    }

}