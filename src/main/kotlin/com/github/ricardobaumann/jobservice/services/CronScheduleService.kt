package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.entities.JobEntity
import org.slf4j.LoggerFactory
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service
import java.util.concurrent.ScheduledFuture

@Service
class CronScheduleService(
    private val taskScheduler: TaskScheduler,
    private val jobExecutionService: JobExecutionService
) {

    companion object {
        private val log = LoggerFactory.getLogger(CronScheduleService::class.java)
    }

    private val localScheduledTriggers = mutableMapOf<String, ScheduledFuture<*>?>()

    fun schedule(scheduleTriggerCommand: ScheduleTriggerCommand) {
        val triggerId = scheduleTriggerCommand.triggerId
        unschedule(triggerId)
        localScheduledTriggers[triggerId] = taskScheduler
            .schedule(
                { jobExecutionService.triggerExecutionFor(scheduleTriggerCommand.jobEntity) },
                CronTrigger(scheduleTriggerCommand.cronString)
            )
    }

    fun unschedule(triggerId: String) {
        localScheduledTriggers.remove(triggerId)
            ?.also {
                log.info("Removing trigger {} from internal schedule", triggerId)
                it.cancel(false)
            }
    }
}

data class ScheduleTriggerCommand(
    val triggerId: String,
    val cronString: String,
    val jobEntity: JobEntity
)