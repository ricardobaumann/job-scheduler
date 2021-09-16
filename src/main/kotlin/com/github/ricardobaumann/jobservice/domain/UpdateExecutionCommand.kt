package com.github.ricardobaumann.jobservice.domain

data class UpdateExecutionCommand(
    val executionStatus: ExecutionStatus,
    val responsePayload: String?
)
