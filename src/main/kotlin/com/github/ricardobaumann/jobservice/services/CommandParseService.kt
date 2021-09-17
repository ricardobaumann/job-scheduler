package com.github.ricardobaumann.jobservice.services

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.ricardobaumann.jobservice.domain.CommandType
import com.github.ricardobaumann.jobservice.domain.HttpCommand
import com.github.ricardobaumann.jobservice.exceptions.CommandParseException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CommandParseService(private val objectMapper: ObjectMapper) {

    companion object {
        private val log = LoggerFactory.getLogger(CommandParseService::class.java)
    }

    fun validate(commandType: CommandType, command: ObjectNode): String {
        parse(commandType, command)
        return command.toString()
    }

    fun parse(commandType: CommandType, command: String): Any =
        parse(commandType, objectMapper.readValue(command, JsonNode::class.java))

    fun parse(commandType: CommandType, command: JsonNode): Any =
        try {
            when (commandType) {
                CommandType.HTTP -> objectMapper.treeToValue(
                    command,
                    HttpCommand::class.java
                )
                else -> {
                    throw CommandParseException(command)
                }
            }
        } catch (e: Exception) {
            log.error("Failed to parse {}", command, e)
            throw CommandParseException(command)
        }

}