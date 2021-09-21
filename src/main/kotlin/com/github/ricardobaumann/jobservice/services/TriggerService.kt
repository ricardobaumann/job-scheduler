package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.controllers.CreateTriggerCommand
import com.github.ricardobaumann.jobservice.entities.JobEntity
import com.github.ricardobaumann.jobservice.entities.JobTriggerEntity
import com.github.ricardobaumann.jobservice.repos.TriggerRepo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class TriggerService(
    private val triggerRepo: TriggerRepo,
    private val jobService: JobService,
    private val cronScheduleService: CronScheduleService
) {
    companion object {
        private val log = LoggerFactory.getLogger(TriggerService::class.java)
    }

    fun create(createTriggerCommand: CreateTriggerCommand) =
        //TODO add validation
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
        ).also { jobTriggerEntity ->
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

    fun delete(id: String) {
        triggerRepo.deleteById(id)
        cronScheduleService.unschedule(id)
    }

    fun findTriggeredBy(jobEntity: JobEntity) =
        triggerRepo.findByTriggeredBy(jobEntity)


}
