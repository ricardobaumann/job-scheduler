package com.github.ricardobaumann.jobservice.controllers

import com.github.ricardobaumann.jobservice.domain.UpdateExecutionCommand
import com.github.ricardobaumann.jobservice.domain.UpdateExecutionResult
import com.github.ricardobaumann.jobservice.services.JobExecutionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/job-executions")
class JobExecutionController(private val jobExecutionService: JobExecutionService) {

    @PutMapping("{id}")
    fun update(@PathVariable id: String, @RequestBody updateExecutionCommand: UpdateExecutionCommand) =
        jobExecutionService.updateExecution(id, updateExecutionCommand)
            ?.let {
                UpdateExecutionResult(
                    it.executionStatus,
                    it.finishedAt,
                    it.id
                )
            }?.let {
                ResponseEntity.noContent()
            } ?: ResponseEntity.notFound()


}