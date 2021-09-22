package com.github.ricardobaumann.jobservice.entities

import com.github.ricardobaumann.jobservice.domain.CommandType
import org.hibernate.Hibernate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name = "jobs")
@EntityListeners(AuditingEntityListener::class)
data class JobEntity(
    @Id val id: String,
    val name: String,

    @Column(name = "command_type")
    @Enumerated(EnumType.STRING) val commandType: CommandType,

    @Embedded val audit: Audit = Audit(),
    val command: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as JobEntity

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
