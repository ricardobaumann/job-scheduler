package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.EventType
import com.github.ricardobaumann.jobservice.domain.TriggerEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TriggerEventService(private val cronScheduleService: CronScheduleService) {

    companion object {
        private val log = LoggerFactory.getLogger(TriggerEventService::class.java)
    }

    fun handleEvent(triggerEvent: TriggerEvent) {
        triggerEvent.triggerEntity
            .also { triggerEntity ->
                when (triggerEvent.eventType) {
                    EventType.CREATED -> {
                        triggerEntity.takeIf { it.isCronTriggered() }?.also {
                            log.info(
                                "Trigger {} is cron based. Will be scheduled internally",
                                triggerEntity
                            )
                            cronScheduleService.schedule(
                                ScheduleTriggerCommand(
                                    triggerId = triggerEntity.id,
                                    cronString = triggerEntity.cronString!!,
                                    jobEntity = triggerEntity.targetJob
                                )
                            )
                        }
                    }
                    EventType.DELETED -> {
                        log.info(
                            "Trigger {} will be removed from schedule",
                            triggerEvent.triggerEntity
                        )
                        cronScheduleService.unschedule(triggerEvent.triggerEntity.id)
                    }
                    else -> {
                        log.info("Unhandled event type: {}", triggerEvent)
                    }
                }
            }
    }


}