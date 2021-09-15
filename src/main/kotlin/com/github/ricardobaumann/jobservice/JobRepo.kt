package com.github.ricardobaumann.jobservice

import org.springframework.data.repository.CrudRepository

interface JobRepo : CrudRepository<JobEntity, String> {
}