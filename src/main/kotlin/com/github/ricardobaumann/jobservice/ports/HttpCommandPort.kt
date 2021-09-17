package com.github.ricardobaumann.jobservice.ports

import com.fasterxml.jackson.databind.JsonNode
import com.github.ricardobaumann.jobservice.domain.ExecutionResult
import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import com.github.ricardobaumann.jobservice.domain.HttpCommand
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class HttpCommandPort(
    private val restTemplate: RestTemplate
) {

    fun execute(httpCommand: HttpCommand, executionId: String) =
        restTemplate.exchange(
            httpCommand.url,
            HttpMethod.valueOf(httpCommand.method),
            buildEntityFor(httpCommand, executionId),
            JsonNode::class.java
        ).let {
            ExecutionResult(
                executionStatus = statusFor(it.statusCode),
                responsePayload = it.body
            )
        }

    private fun statusFor(statusCode: HttpStatus) =
        if (statusCode.is2xxSuccessful) {
            ExecutionStatus.SUCCESS
        } else {
            ExecutionStatus.FAILURE
        }

    private fun buildEntityFor(httpCommand: HttpCommand, executionId: String): HttpEntity<CommandPayload> {
        val headers = HttpHeaders()
        httpCommand.headers.forEach { (key, value) ->
            headers[key] = value
        }
        return HttpEntity(
            CommandPayload(
                executionId = executionId,
                operationName = httpCommand.operationName,
                body = httpCommand.body
            ), headers
        )
    }
}

data class CommandPayload(
    val executionId: String,
    val operationName: String,
    val body: JsonNode
)

