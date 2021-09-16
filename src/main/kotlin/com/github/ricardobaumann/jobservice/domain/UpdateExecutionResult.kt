package com.github.ricardobaumann.jobservice.domain

import java.time.LocalDateTime

data class UpdateExecutionResult(
    val executionStatus: ExecutionStatus,
    val finishedAt: LocalDateTime?,
    val id: String
)
