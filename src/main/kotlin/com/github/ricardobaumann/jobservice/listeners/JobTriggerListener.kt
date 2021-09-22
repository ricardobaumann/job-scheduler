package com.github.ricardobaumann.jobservice.listeners

import com.github.ricardobaumann.jobservice.domain.TriggerEvent
import com.github.ricardobaumann.jobservice.services.TriggerEventService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class JobTriggerListener(private val triggerEventService: TriggerEventService) {

    @Async
    @EventListener
    fun handle(triggerEvent: TriggerEvent) {
        triggerEventService.handleEvent(triggerEvent)
    }

}