package com.github.ricardobaumann.jobservice.domain

import javax.persistence.*

@Entity
data class JobTriggerEntity(
    @Id val id: String,
    val cronString: String?,
    @ManyToOne val jobOwner: JobEntity,
    @ManyToOne val triggeredBy: JobEntity?,
    @Enumerated(EnumType.STRING) val executionStatus: ExecutionStatus?
) {
    fun isCronTriggered() = cronString != null
}