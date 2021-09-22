package com.github.ricardobaumann.jobservice.entities

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EntityListeners

@Embeddable
@EntityListeners(AuditingEntityListener::class)
data class Audit(
    @Column(name = "created_by") @field:CreatedBy var createdBy: String? = null,
    @Column(name = "modified_by") @field:LastModifiedBy var modifiedBy: String? = null,
    @Column(name = "created_at") @field:CreatedDate var createdAt: LocalDateTime? = null,
    @Column(name = "modified_at") @field:LastModifiedDate var modifiedAt: LocalDateTime? = null
)