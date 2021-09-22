package com.github.ricardobaumann.jobservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.ricardobaumann.jobservice.controllers.CreateTriggerCommand
import com.github.ricardobaumann.jobservice.domain.CommandType
import com.github.ricardobaumann.jobservice.domain.HttpCommand
import com.github.ricardobaumann.jobservice.domain.JobCreateCommand
import com.github.ricardobaumann.jobservice.services.JobService
import com.github.ricardobaumann.jobservice.services.TriggerService
import org.springframework.boot.CommandLineRunner
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import java.util.*

@Component
class Init(
    private val jobService: JobService,
    private val objectMapper: ObjectMapper,
    private val triggerService: TriggerService
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        //TODO load jobs from database

        val auth = Base64.getEncoder().encodeToString("admin:admin".toByteArray())

        for (i in 0..200) {
            jobService.create(
                JobCreateCommand(
                    name = "test_$i",
                    commandType = CommandType.HTTP,
                    command = objectMapper.valueToTree(
                        HttpCommand(
                            method = "POST",
                            url = "http://localhost:8080/jobs-client",
                            operationName = "just do it $i",
                            body = objectMapper.createObjectNode(),
                            headers = mapOf(
                                "Content-Type" to "application/json",
                                "Accept" to "application/json",
                                HttpHeaders.AUTHORIZATION to auth
                            )
                        )
                    )
                )
            ).also { jobEntity ->
                triggerService.create(
                    CreateTriggerCommand(
                        jobId = jobEntity.id,
                        cronString = "0/5 * * ? * *"
                    )
                )
            }
        }
    }
}