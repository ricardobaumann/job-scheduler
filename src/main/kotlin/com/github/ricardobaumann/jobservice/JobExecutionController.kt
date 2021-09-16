package com.github.ricardobaumann.jobservice

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