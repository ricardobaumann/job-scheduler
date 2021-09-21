package com.github.ricardobaumann.jobservice.entities

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Embeddable
import javax.persistence.EntityListeners

@Embeddable
@EntityListeners(AuditingEntityListener::class)
data class Audit(
    @field:CreatedBy var createdBy: String? = null,
    @field:LastModifiedBy var modifiedBy: String? = null,
    @field:CreatedDate var createdAt: LocalDateTime? = null,
    @field:LastModifiedDate var modifiedAt: LocalDateTime? = null
)