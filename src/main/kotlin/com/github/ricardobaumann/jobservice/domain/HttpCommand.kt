package com.github.ricardobaumann.jobservice.domain

import com.fasterxml.jackson.databind.JsonNode

data class HttpCommand(
    val method: String,
    val url: String,
    val operationName: String,
    val body: JsonNode,
    val headers: Map<String, String>
)