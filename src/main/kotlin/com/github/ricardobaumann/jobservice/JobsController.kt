package com.github.ricardobaumann.jobservice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/jobs")
class JobsController(private val jobService: JobService) {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody jobCreateCommand: JobCreateCommand) =
        JobCreateResult(jobService.create(jobCreateCommand).id)

}