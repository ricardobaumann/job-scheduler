package com.github.ricardobaumann.jobservice.controllers

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import com.github.ricardobaumann.jobservice.services.TriggerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/triggers")
class TriggerController(private val triggerService: TriggerService) {
    
    @PostMapping
    fun create(@RequestBody createTriggerCommand: CreateTriggerCommand) =
        CreateTriggerResponse(id = triggerService.create(createTriggerCommand).id)


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        triggerService.delete(id)
    }
}

data class CreateTriggerResponse(
    val id: String
)

data class CreateTriggerCommand(
    val jobId: String,
    val cronString: String? = null,
    val triggeredBy: String? = null,
    val triggeredOn: ExecutionStatus? = null
)
