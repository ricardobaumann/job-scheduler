package com.github.ricardobaumann.jobservice

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface JobExecutionRepo : CrudRepository<JobExecutionEntity, String>