package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.ExecutionCommand
import com.github.ricardobaumann.jobservice.domain.ExecutionResult
import com.github.ricardobaumann.jobservice.domain.HttpCommand
import com.github.ricardobaumann.jobservice.exceptions.UnknownCommandException
import com.github.ricardobaumann.jobservice.ports.HttpCommandPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CommandExecutionService(
    private val commandParseService: CommandParseService,
    private val httpCommandService: HttpCommandPort
) {

    companion object {
        private val log = LoggerFactory.getLogger(CommandExecutionService::class.java)
    }

    fun execute(executionCommand: ExecutionCommand): ExecutionResult {
        log.info("Execution command: {}", executionCommand)
        val parsedCommand = commandParseService.parse(
            executionCommand.commandType,
            executionCommand.command
        )
        log.info("Executing: {}", parsedCommand)
        return when (parsedCommand) {
            is HttpCommand -> httpCommandService.execute(parsedCommand, executionCommand.executionId)
            else -> {
                throw UnknownCommandException(executionCommand)
            }
        }
    }

}