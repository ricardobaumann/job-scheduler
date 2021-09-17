package com.github.ricardobaumann.jobservice.exceptions

import com.github.ricardobaumann.jobservice.domain.ExecutionCommand

class UnknownCommandException(executionCommand: ExecutionCommand) : RuntimeException("Unable to run $executionCommand")
