package com.github.ricardobaumann.jobservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class PersistenceConfig {

    @Bean
    fun auditorProvider() = JobServiceAuditorProvider()

}

class JobServiceAuditorProvider : AuditorAware<String> {
    override fun getCurrentAuditor() = Optional.of("the-user")
}