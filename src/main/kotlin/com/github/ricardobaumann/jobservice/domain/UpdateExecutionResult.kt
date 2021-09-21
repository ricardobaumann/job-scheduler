package com.github.ricardobaumann.jobservice.domain

data class UpdateExecutionResult(
    val executionStatus: ExecutionStatus,
    val id: String
)
