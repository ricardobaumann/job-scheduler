package com.github.ricardobaumann.jobservice.entities

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class JobTriggerEntity(
    @Id val id: String,
    val cronString: String?,
    @ManyToOne val targetJob: JobEntity,
    @ManyToOne val triggeredBy: JobEntity?,
    @Embedded val audit: Audit = Audit(),
    @Enumerated(EnumType.STRING) val executionStatus: ExecutionStatus?
) {
    @Transient
    fun isCronTriggered() = cronString != null
}