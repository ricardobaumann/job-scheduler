package com.github.ricardobaumann.jobservice.entities

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import org.hibernate.Hibernate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class JobExecutionEntity(
    @Id val id: String,
    @ManyToOne val jobEntity: JobEntity,
    val startedAt: LocalDateTime,
    @Embedded val audit: Audit = Audit(),
    @Enumerated(EnumType.STRING) var executionStatus: ExecutionStatus,
    var responsePayload: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as JobExecutionEntity

        return id == other.id
    }

    override fun hashCode(): Int = 0
}