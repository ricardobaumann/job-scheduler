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
        cancel(jobEntity)
        scheduledMap[jobEntity.id] = taskScheduler.schedule({
            jobExecutionService.triggerExecutionFor(jobEntity)
        }, CronTrigger(jobEntity.cronString))
    }

    fun cancel(jobEntity: JobEntity) {
        scheduledMap.remove(jobEntity.id)?.also {
            it.cancel(false)
        }
    }

}