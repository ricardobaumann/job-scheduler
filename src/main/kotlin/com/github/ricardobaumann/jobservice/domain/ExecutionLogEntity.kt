package com.github.ricardobaumann.jobservice.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class ExecutionLogEntity(
    @Id val id: String,
    @ManyToOne val jobExecutionEntity: JobExecutionEntity,
    val receivedAt: LocalDateTime = LocalDateTime.now(),
    val executionStatus: ExecutionStatus = ExecutionStatus.RUNNING,
    val responsePayload: String? = null
)
