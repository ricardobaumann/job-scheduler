package com.github.ricardobaumann.jobservice.repos

import com.github.ricardobaumann.jobservice.domain.JobExecutionEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface JobExecutionRepo : CrudRepository<JobExecutionEntity, String>