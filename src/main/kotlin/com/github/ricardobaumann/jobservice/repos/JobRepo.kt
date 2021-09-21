package com.github.ricardobaumann.jobservice.repos

import com.github.ricardobaumann.jobservice.entities.JobEntity
import org.springframework.data.repository.CrudRepository

interface JobRepo : CrudRepository<JobEntity, String> {
}