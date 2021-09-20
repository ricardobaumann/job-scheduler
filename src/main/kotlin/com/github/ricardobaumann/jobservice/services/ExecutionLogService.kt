package com.github.ricardobaumann.jobservice.services

import com.github.ricardobaumann.jobservice.domain.ExecutionLogEntity
import com.github.ricardobaumann.jobservice.domain.JobExecutionEntity
import com.github.ricardobaumann.jobservice.repos.ExecutionLogRepo
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExecutionLogService(private val executionLogRepo: ExecutionLogRepo) {

    fun logFor(jobExecutionEntity: JobExecutionEntity) =
        executionLogRepo.save(
            ExecutionLogEntity(
                id = UUID.randomUUID().toString(),
                jobExecutionEntity = jobExecutionEntity,
                executionStatus = jobExecutionEntity.executionStatus,
                responsePayload = jobExecutionEntity.responsePayload
            )
        )
}