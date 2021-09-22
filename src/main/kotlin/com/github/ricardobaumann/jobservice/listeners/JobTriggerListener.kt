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
        triggerEvent.triggerEntity
            .takeIf { it.isCronTriggered() }
            ?.also { triggerEntity ->
                when (triggerEvent.eventType) {
                    EventType.CREATED -> {
                        log.info("Trigger {} is cron based. Will be scheduled internally", triggerEntity)
                        cronScheduleService.schedule(
                            ScheduleTriggerCommand(
                                triggerId = triggerEntity.targetJob.id,
                                cronString = triggerEntity.cronString!!,
                                jobEntity = triggerEntity.targetJob
                            )
                        )
                    }
                    EventType.DELETED -> {
                        log.info("Trigger {} will be removed from schedule", triggerEvent.triggerEntity)
                        cronScheduleService.unschedule(triggerEvent.triggerEntity.id)
                    }
                    else -> {
                        log.info("Unhandled event type: {}", triggerEvent)
                    }
                }
            }
    }

}