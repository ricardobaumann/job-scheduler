package com.github.ricardobaumann.jobservice.domain

import com.github.ricardobaumann.jobservice.entities.JobTriggerEntity

data class TriggerEvent(
    val eventType: EventType,
    val triggerEntity: JobTriggerEntity
)

