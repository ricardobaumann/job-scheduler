package com.github.ricardobaumann.jobservice.listeners

import com.github.ricardobaumann.jobservice.services.CronScheduleService
import com.github.ricardobaumann.jobservice.services.ScheduleTriggerCommand
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class JobTriggerListener(private val cronScheduleService: CronScheduleService) {

    companion object {
        private val log = LoggerFactory.getLogger(JobTriggerListener::class.java)
    }

    @Async
    @EventListener
    fun handle(triggerEvent: TriggerEvent) {
        val jobTriggerEntity = triggerEvent.triggerEntity
        when (triggerEvent.eventType) {
            EventType.CREATED -> {
                jobTriggerEntity.takeIf { it.isCronTriggered() }
                    ?.also {
                        log.info("Trigger {} is cron based. Will be scheduled internally", it)
                        cronScheduleService.schedule(
                            ScheduleTriggerCommand(
                                triggerId = it.targetJob.id,
                                cronString = it.cronString!!,
                                jobEntity = it.targetJob
                            )
                        )
                    }
            }
            EventType.DELETED -> {
                cronScheduleService.unschedule(triggerEvent.triggerEntity.id)
            }
            else -> {
                log.info("Unhandled event type: {}", triggerEvent)
            }
        }
    }

}