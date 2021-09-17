package com.github.ricardobaumann.jobservice.domain

import com.fasterxml.jackson.databind.node.ObjectNode

data class HttpCommand(
    val method: String,
    val url: String,
    val body: ObjectNode,
    val headers: Map<String, String>
)