package com.github.ricardobaumann.jobservice

import com.fasterxml.jackson.databind.node.ObjectNode

data class JobCreateCommand(
    val cronString: String,
    val name: String,
    val commandType: CommandType,
    val command: ObjectNode
)
