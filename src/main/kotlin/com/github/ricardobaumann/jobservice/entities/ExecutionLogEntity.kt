package com.github.ricardobaumann.jobservice.entities

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name = "execution_logs")
@EntityListeners(AuditingEntityListener::class)
data class ExecutionLogEntity(
    @Id val id: String,
    @JoinColumn(name = "execution_id") @ManyToOne val jobExecutionEntity: JobExecutionEntity,
    @Embedded val audit: Audit = Audit(),
    @Column(name = "execution_status") val executionStatus: ExecutionStatus = ExecutionStatus.RUNNING,
    @Column(name = "response_payload") val responsePayload: String? = null
)
