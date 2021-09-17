package com.github.ricardobaumann.jobservice.services

import org.springframework.scheduling.support.CronExpression
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CronService {

    fun getNextExecutionFor(cronString: String, truncatedNow: LocalDateTime) =
        CronExpression.parse(cronString).let {
            it.next(truncatedNow)!!

        }
}