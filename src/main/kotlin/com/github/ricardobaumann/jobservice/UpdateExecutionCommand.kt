package com.github.ricardobaumann.jobservice

data class UpdateExecutionCommand(
    val executionStatus: ExecutionStatus,
    val responsePayload: String?
)
