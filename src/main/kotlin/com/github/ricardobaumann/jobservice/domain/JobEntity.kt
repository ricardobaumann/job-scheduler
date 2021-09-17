package com.github.ricardobaumann.jobservice.domain

import org.hibernate.Hibernate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity
data class JobEntity(
    @Id val id: String,
    val name: String,
    val cronString: String,
    @Enumerated(EnumType.STRING) val commandType: CommandType,
    val command: String,
    @Enumerated(EnumType.STRING) var lastStatus: ExecutionStatus,
    var nextExecution: LocalDateTime
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as JobEntity

        return id == other.id
    }

    override fun hashCode(): Int = 0
}
