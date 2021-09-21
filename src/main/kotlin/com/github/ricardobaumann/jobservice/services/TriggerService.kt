package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.controllers.CreateTriggerCommand
import com.github.ricardobaumann.jobservice.entities.JobEntity
import com.github.ricardobaumann.jobservice.entities.JobTriggerEntity
import com.github.ricardobaumann.jobservice.listeners.EventType
import com.github.ricardobaumann.jobservice.listeners.TriggerEvent
import com.github.ricardobaumann.jobservice.repos.TriggerRepo
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class TriggerService(
    private val triggerRepo: TriggerRepo,
    private val jobService: JobService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    companion object {
        private val log = LoggerFactory.getLogger(TriggerService::class.java)
    }

    fun create(createTriggerCommand: CreateTriggerCommand) =
        triggerRepo.save(
            JobTriggerEntity(
                id = UUID.randomUUID().toString(),
                cronString = createTriggerCommand.cronString,
                targetJob = createTriggerCommand.jobId.let {
                    jobService.findByIdOrFail(it)
                },
                triggeredBy = createTriggerCommand.triggeredBy?.let {
                    jobService.findByIdOrFail(it)
                },
                executionStatus = createTriggerCommand.triggeredOn
            )
        ).also {
            log.info("Trigger {} created successfully", it)
            applicationEventPublisher.publishEvent(
                TriggerEvent(
                    eventType = EventType.CREATED,
                    triggerEntity = it
                )
            )
        }

    fun delete(id: String) {
        triggerRepo.findByIdOrNull(id)
            ?.also {
                triggerRepo.delete(it)
                applicationEventPublisher.publishEvent(
                    TriggerEvent(
                        eventType = EventType.DELETED,
                        triggerEntity = it
                    )
                )
            }
    }

    fun findTriggeredBy(jobEntity: JobEntity) =
        triggerRepo.findByTriggeredBy(jobEntity)


}
