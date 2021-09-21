package com.github.ricardobaumann.jobservice.repos

import com.github.ricardobaumann.jobservice.domain.JobTriggerEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TriggerRepo : CrudRepository<JobTriggerEntity, String>
