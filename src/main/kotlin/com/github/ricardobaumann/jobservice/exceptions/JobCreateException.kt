package com.github.ricardobaumann.jobservice.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class JobCreateException(e: Exception) : RuntimeException(e)
