package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.JobCreateCommand
import com.github.ricardobaumann.jobservice.domain.JobEntity
import com.github.ricardobaumann.jobservice.repos.JobRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*


@Service
class JobService(
    private val jobRepo: JobRepo,
    private val commandParseService: CommandParseService,
    private val scheduleService: ScheduleService
) {
    fun create(jobCreateCommand: JobCreateCommand) =
        jobRepo.save(
            JobEntity(
                id = UUID.randomUUID().toString(),
                name = jobCreateCommand.name,
                cronString = jobCreateCommand.cronString,
                commandType = jobCreateCommand.commandType,
                command = commandParseService.validate(
                    jobCreateCommand.commandType,
                    jobCreateCommand.command
                )
            )
        ).also {
            scheduleService.schedule(it)
        }

    fun delete(jobId: String) =
        jobRepo.findByIdOrNull(jobId)
            ?.also {
                //jobRepo.delete(it) or soft delete?
                scheduleService.cancel(it)
            }

    fun findAll(): Iterable<JobEntity> = jobRepo.findAll()

}
