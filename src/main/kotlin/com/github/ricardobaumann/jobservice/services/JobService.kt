package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.JobCreateCommand
import com.github.ricardobaumann.jobservice.entities.JobEntity
import com.github.ricardobaumann.jobservice.exceptions.JobCreateException
import com.github.ricardobaumann.jobservice.exceptions.JobNotFoundException
import com.github.ricardobaumann.jobservice.repos.JobRepo
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class JobService(
    private val jobRepo: JobRepo,
    private val commandParseService: CommandParseService
) {

    companion object {
        private val log = LoggerFactory.getLogger(JobService::class.java)
    }
    
    fun create(jobCreateCommand: JobCreateCommand) =
        try {
            jobRepo.save(
                JobEntity(
                    id = UUID.randomUUID().toString(),
                    name = jobCreateCommand.name,
                    commandType = jobCreateCommand.commandType,
                    command = commandParseService.validate(
                        jobCreateCommand.commandType,
                        jobCreateCommand.command
                    )
                )
            )
        } catch (e: Exception) {
            log.error("Failed to create job: {}", jobCreateCommand, e)
            throw JobCreateException(e)
        }

    fun delete(jobId: String) {
        throw UnsupportedOperationException("Not implemented")
    }

    fun findByIdOrFail(id: String) = jobRepo.findByIdOrNull(id) ?: throw JobNotFoundException(id)

    fun findAll(): Iterable<JobEntity> = jobRepo.findAll()

}
