package com.github.ricardobaumann.jobservice.domain

import com.fasterxml.jackson.databind.node.ObjectNode

data class ExecutionResult(
    val executionStatus: ExecutionStatus,
    val responsePayload: ObjectNode
)