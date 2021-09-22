package com.github.ricardobaumann.jobservice.controllers

import com.fasterxml.jackson.annotation.JsonUnwrapped
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.ricardobaumann.jobservice.domain.CommandType
import com.github.ricardobaumann.jobservice.domain.JobCreateCommand
import com.github.ricardobaumann.jobservice.domain.JobCreateResult
import com.github.ricardobaumann.jobservice.entities.Audit
import com.github.ricardobaumann.jobservice.services.JobService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/jobs")
class JobsController(
    private val jobService: JobService,
    private val objectMapper: ObjectMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody jobCreateCommand: JobCreateCommand) =
        JobCreateResult(jobService.create(jobCreateCommand).id)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        jobService.delete(id)
    }

    @GetMapping
    fun list() = jobService.findAll()
        .map {
            JobInfo(
                id = it.id,
                name = it.name,
                commandType = it.commandType,
                command = objectMapper.readTree(it.command),
                audit = it.audit
            )
        }

}

data class JobInfo(
    val id: String,
    val name: String,
    val commandType: CommandType,
    @JsonUnwrapped
    val audit: Audit = Audit(),
    val command: JsonNode
)