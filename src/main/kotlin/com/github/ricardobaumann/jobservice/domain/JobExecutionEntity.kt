package com.github.ricardobaumann.jobservice.domain

import org.hibernate.Hibernate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class JobExecutionEntity(
    @Id val id: String,
    @ManyToOne val jobEntity: JobEntity,
    val startedAt: LocalDateTime,
    var finishedAt: LocalDateTime? = null,
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