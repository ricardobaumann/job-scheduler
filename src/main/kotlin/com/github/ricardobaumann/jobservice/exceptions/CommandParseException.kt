package com.github.ricardobaumann.jobservice.exceptions

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class CommandParseException(command: JsonNode) :
    RuntimeException("Unable to parse ${command.toPrettyString()}")
