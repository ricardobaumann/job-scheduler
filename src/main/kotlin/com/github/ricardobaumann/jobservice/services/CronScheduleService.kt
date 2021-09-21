package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.JobEntity
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service
import java.util.concurrent.ScheduledFuture

@Service
class CronScheduleService(
    private val taskScheduler: TaskScheduler,
    private val jobExecutionService: JobExecutionService
) {
    private val scheduledMap = mutableMapOf<String, ScheduledFuture<*>?>()

    fun schedule(scheduleTriggerCommand: ScheduleTriggerCommand) {
        val triggerId = scheduleTriggerCommand.triggerId
        remove(triggerId)
        scheduledMap[triggerId] = taskScheduler
            .schedule(
                { jobExecutionService.triggerExecutionFor(scheduleTriggerCommand.jobEntity) },
                CronTrigger(scheduleTriggerCommand.cronString)
            )
    }

    fun unschedule(triggerId: String) {
        remove(triggerId)
    }

    private fun remove(triggerId: String) {
        scheduledMap.remove(triggerId)
            ?.also {
                it.cancel(false)
            }
    }
}

data class ScheduleTriggerCommand(
    val triggerId: String,
    val cronString: String,
    val jobEntity: JobEntity
)