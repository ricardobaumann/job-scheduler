package com.github.ricardobaumann.jobservice.repos

import com.github.ricardobaumann.jobservice.entities.JobEntity
import com.github.ricardobaumann.jobservice.entities.JobTriggerEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TriggerRepo : CrudRepository<JobTriggerEntity, String> {
    fun findByTriggeredBy(jobEntity: JobEntity): List<JobTriggerEntity>
}
