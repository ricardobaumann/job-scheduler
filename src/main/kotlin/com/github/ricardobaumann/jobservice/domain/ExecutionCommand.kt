package com.github.ricardobaumann.jobservice.domain

data class ExecutionCommand(
    val executionId: String,
    val commandType: CommandType,
    val command: String
)
