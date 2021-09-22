package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.JobCreateCommand
import com.github.ricardobaumann.jobservice.entities.JobEntity
import com.github.ricardobaumann.jobservice.exceptions.JobNotFoundException
import com.github.ricardobaumann.jobservice.repos.JobRepo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*


@Service
class JobService(
    private val jobRepo: JobRepo,
    private val commandParseService: CommandParseService
) {
    fun create(jobCreateCommand: JobCreateCommand) =
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

    fun delete(jobId: String) {
        throw UnsupportedOperationException("Not implemented")
    }

    fun findByIdOrFail(id: String) = jobRepo.findByIdOrNull(id) ?: throw JobNotFoundException(id)
    fun findAll() = jobRepo.findAll()

}
