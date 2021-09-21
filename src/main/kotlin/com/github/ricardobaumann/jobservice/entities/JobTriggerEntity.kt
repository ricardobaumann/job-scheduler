package com.github.ricardobaumann.jobservice.entities

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import javax.persistence.*

@Entity
data class JobTriggerEntity(
    @Id val id: String,
    val cronString: String?,
    @ManyToOne val targetJob: JobEntity,
    @ManyToOne val triggeredBy: JobEntity?,
    @Enumerated(EnumType.STRING) val executionStatus: ExecutionStatus?
) {
    @Transient
    fun isCronTriggered() = cronString != null
}