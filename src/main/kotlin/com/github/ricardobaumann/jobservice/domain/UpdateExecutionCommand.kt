package com.github.ricardobaumann.jobservice.domain

import com.fasterxml.jackson.databind.node.ObjectNode

data class UpdateExecutionCommand(
    val executionStatus: ExecutionStatus,
    val responsePayload: ObjectNode?
)
