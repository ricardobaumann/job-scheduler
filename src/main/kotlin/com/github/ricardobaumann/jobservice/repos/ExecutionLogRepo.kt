package com.github.ricardobaumann.jobservice.repos

import com.github.ricardobaumann.jobservice.entities.ExecutionLogEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ExecutionLogRepo : CrudRepository<ExecutionLogEntity, String>