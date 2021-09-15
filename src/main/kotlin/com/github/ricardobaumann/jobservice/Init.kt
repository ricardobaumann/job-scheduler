package com.github.ricardobaumann.jobservice

import com.fasterxml.jackson.databind.ObjectMapper
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
                command = objectMapper.createObjectNode()
            )
        )
    }
}