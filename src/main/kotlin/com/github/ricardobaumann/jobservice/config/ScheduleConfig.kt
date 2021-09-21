package com.github.ricardobaumann.jobservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableAsync
@Configuration
@EnableScheduling
class ScheduleConfig