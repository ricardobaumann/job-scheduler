package com.github.ricardobaumann.jobservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.ricardobaumann.jobservice.domain.CommandType
import com.github.ricardobaumann.jobservice.domain.HttpCommand
import com.github.ricardobaumann.jobservice.domain.JobCreateCommand
import com.github.ricardobaumann.jobservice.services.JobService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Init(
    private val jobService: JobService,
    private val objectMapper: ObjectMapper
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        jobService.create(
            JobCreateCommand(
                cronString = "0/5 * * ? * *",
                name = "test",
                commandType = CommandType.HTTP,
                command = objectMapper.valueToTree(
                    HttpCommand(
                        method = "POST",
                        url = "http://localhost:8080/jobs-client",
                        body = objectMapper.createObjectNode(),
                        headers = mapOf()
                    )
                )
            )
        )
    }
}