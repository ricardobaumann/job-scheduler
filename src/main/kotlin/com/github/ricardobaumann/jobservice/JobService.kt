package com.github.ricardobaumann.jobservice

import org.springframework.scheduling.support.CronExpression
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class JobService(private val jobRepo: JobRepo) {
    fun create(jobCreateCommand: JobCreateCommand) =
        jobRepo.save(
            JobEntity(
                id = UUID.randomUUID().toString(),
                name = jobCreateCommand.name,
                cronString = jobCreateCommand.cronString,
                commandType = jobCreateCommand.commandType,
                command = jobCreateCommand.command.toString(),
                lastStatus = JobStatus.NONE,
                nextExecution = getNextExecutionFor(jobCreateCommand.cronString)
            )
        ).let {
            JobCreateResult(it.id)
        }

    private fun getNextExecutionFor(cronString: String) =
        CronExpression.parse(cronString).let {
            it.next(LocalDateTime.now())!!
        }

    fun findAll(): Iterable<JobEntity> = jobRepo.findAll()

    fun updateExecution(jobEntity: JobEntity): JobEntity {
        jobEntity.nextExecution = getNextExecutionFor(jobEntity.cronString)
        return jobRepo.save(jobEntity)
    }

}
