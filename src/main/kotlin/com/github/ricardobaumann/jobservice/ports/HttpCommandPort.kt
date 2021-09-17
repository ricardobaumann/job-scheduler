package com.github.ricardobaumann.jobservice.ports

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.ricardobaumann.jobservice.domain.ExecutionResult
import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import com.github.ricardobaumann.jobservice.domain.HttpCommand
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class HttpCommandPort(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper
) {

    fun execute(httpCommand: HttpCommand, executionId: String) =
        //TODO execute http command
        ExecutionResult(
            executionStatus = ExecutionStatus.SUCCESS,
            responsePayload = objectMapper.createObjectNode()
        )
}

