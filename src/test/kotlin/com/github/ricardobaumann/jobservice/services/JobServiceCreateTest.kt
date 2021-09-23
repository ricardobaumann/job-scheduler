package com.github.ricardobaumann.jobservice.services

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.ricardobaumann.jobservice.domain.CommandType
import com.github.ricardobaumann.jobservice.domain.HttpCommand
import com.github.ricardobaumann.jobservice.domain.JobCreateCommand
import com.github.ricardobaumann.jobservice.entities.JobEntity
import com.github.ricardobaumann.jobservice.exceptions.CommandParseException
import com.github.ricardobaumann.jobservice.exceptions.JobCreateException
import com.github.ricardobaumann.jobservice.repos.JobRepo
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
@DisplayName("When a job is created")
internal class JobServiceCreateTest {

    @Mock
    lateinit var jobRepo: JobRepo
    private val objectMapper = jacksonObjectMapper()

    @Mock
    lateinit var commandParseService: CommandParseService

    @InjectMocks
    lateinit var jobService: JobService

    private val entity = JobEntity(
        id = "1",
        name = "test",
        commandType = CommandType.HTTP,
        command = ""
    )

    @Test
    @DisplayName("it should be persisted if the command is valid")
    fun persist() {
        `when`(jobRepo.save(any()))
            .thenReturn(entity)
        val command = objectMapper.valueToTree<ObjectNode>(
            HttpCommand(
                method = "POST",
                url = "some-url",
                operationName = "some name",
                body = objectMapper.createObjectNode().put("foo", "bar"),
                headers = mapOf(
                    "foo" to "bar"
                )
            )
        )
        `when`(
            commandParseService.validate(
                CommandType.HTTP,
                command
            )
        ).thenReturn(command.toString())

        val result = jobService.create(
            JobCreateCommand(
                name = "test",
                commandType = CommandType.HTTP,
                command = command
            )
        )
        assertThat(result).isEqualTo(entity)
    }

    @Test
    @DisplayName("it should fail when the command is not recognized")
    fun fail() {
        val command = objectMapper.valueToTree<ObjectNode>(
            HttpCommand(
                method = "POST",
                url = "some-url",
                operationName = "some name",
                body = objectMapper.createObjectNode().put("foo", "bar"),
                headers = mapOf(
                    "foo" to "bar"
                )
            )
        )

        doThrow(CommandParseException::class.java)
            .`when`(commandParseService)
            .validate(
                CommandType.HTTP,
                command
            )

        assertThatThrownBy {
            jobService.create(
                JobCreateCommand(
                    name = "test",
                    commandType = CommandType.HTTP,
                    command = command
                )
            )
        }.isInstanceOf(JobCreateException::class.java)
        verify(jobRepo, never()).save(any())
    }
}