package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import com.github.ricardobaumann.jobservice.domain.JobCreateCommand
import com.github.ricardobaumann.jobservice.domain.JobEntity
import com.github.ricardobaumann.jobservice.repos.JobRepo
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
                lastStatus = ExecutionStatus.NONE,
                nextExecution = getNextExecutionFor(jobCreateCommand.cronString)
            )
        )

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
