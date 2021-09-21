package com.github.ricardobaumann.jobservice.entities

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class ExecutionLogEntity(
    @Id val id: String,
    @ManyToOne val jobExecutionEntity: JobExecutionEntity,
    @Embedded val audit: Audit = Audit(),
    val executionStatus: ExecutionStatus = ExecutionStatus.RUNNING,
    val responsePayload: String? = null
)
