package com.github.ricardobaumann.jobservice

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/jobs")
class JobsController(private val jobService: JobService) {

    companion object {
        private val log = LoggerFactory.getLogger(JobsController::class.java)
    }

    @PostMapping
    fun create(@RequestBody jobCreateCommand: JobCreateCommand): JobCreateResult {
        log.info("Received: {}", jobCreateCommand)
        return jobService.create(jobCreateCommand)
    }

}