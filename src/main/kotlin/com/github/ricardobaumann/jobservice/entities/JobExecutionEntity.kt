package com.github.ricardobaumann.jobservice.entities

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import org.hibernate.Hibernate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "job_executions")
@EntityListeners(AuditingEntityListener::class)
data class JobExecutionEntity(
    @Id val id: String,
    @JoinColumn(name = "job_id") @ManyToOne val jobEntity: JobEntity,
    @Column(name = "started_at") val startedAt: LocalDateTime,
    @Embedded val audit: Audit = Audit(),
    @Column(name = "execution_status") @Enumerated(EnumType.STRING) var executionStatus: ExecutionStatus,
    @Column(name = "response_payload") var responsePayload: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as JobExecutionEntity

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}