package com.github.ricardobaumann.jobservice.domain

import com.fasterxml.jackson.databind.JsonNode

data class ExecutionResult(
    val executionStatus: ExecutionStatus,
    val responsePayload: JsonNode?
)