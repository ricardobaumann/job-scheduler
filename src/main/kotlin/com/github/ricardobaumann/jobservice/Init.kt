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
        //TODO load jobs from database
        jobService.create(
            JobCreateCommand(
                cronString = "0/5 * * ? * *",
                name = "test",
                commandType = CommandType.HTTP,
                command = objectMapper.valueToTree(
                    HttpCommand(
                        method = "POST",
                        url = "http://localhost:8080/jobs-client",
                        operationName = "just do it",
                        body = objectMapper.createObjectNode(),
                        headers = mapOf(
                            "Content-Type" to "application/json",
                            "Accept" to "application/json"
                        )
                    )
                )
            )
        )


        jobService.create(
            JobCreateCommand(
                cronString = "0/10 * * ? * *",
                name = "second test",
                commandType = CommandType.HTTP,
                command = objectMapper.valueToTree(
                    HttpCommand(
                        method = "POST",
                        url = "http://localhost:8080/jobs-client",
                        operationName = "just do it again",
                        body = objectMapper.createObjectNode(),
                        headers = mapOf(
                            "Content-Type" to "application/json",
                            "Accept" to "application/json"
                        )
                    )
                )
            )
        )
    }
}