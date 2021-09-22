package com.github.ricardobaumann.jobservice.entities

import com.github.ricardobaumann.jobservice.domain.ExecutionStatus
import org.hibernate.Hibernate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name = "triggers")
@EntityListeners(AuditingEntityListener::class)
data class JobTriggerEntity(
    @Id val id: String,
    @Column(name = "cron_string") val cronString: String?,
    @JoinColumn(name = "target_job_id") @ManyToOne val targetJob: JobEntity,
    @JoinColumn(name = "triggered_by_job_id") @ManyToOne val triggeredBy: JobEntity?,
    @Embedded val audit: Audit = Audit(),
    @Column(name = "execution_status") @Enumerated(EnumType.STRING) val executionStatus: ExecutionStatus?
) {
    @Transient
    fun isCronTriggered() = cronString != null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as JobTriggerEntity

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}