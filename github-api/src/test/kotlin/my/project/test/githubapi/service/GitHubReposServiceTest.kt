package my.project.test.githubapi.service

import com.fasterxml.jackson.databind.ObjectMapper
import my.project.test.githubapi.model.Consumer
import my.project.test.githubapi.webClient.ResponseDetails
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.concurrent.CompletableFuture
import kotlin.test.assertEquals

internal class GitHubReposServiceTest {
    private lateinit var customObjectMapper: ObjectMapper
    private lateinit var requestToGitHubService: RequestToGitHubService
    private lateinit var gitHubReposService: GitHubReposService

    @BeforeEach
    fun setUp() {
        customObjectMapper = mock()
        requestToGitHubService = mock()
        gitHubReposService = GitHubReposService(customObjectMapper, requestToGitHubService)
    }

    @Test
    fun `should return the response entity with status Not found`() {
        //given
        val requestBody = "requestBody"
        val userName = Consumer("userName")
        val prefix = "/users/userName/repos"
        val responseDetails = ResponseDetails(ResponseEntity<String>("body", HttpStatus.NOT_FOUND))
        given(customObjectMapper.readValue(requestBody, Consumer::class.java)).willReturn(userName)
        given(requestToGitHubService.process(prefix)).willReturn(CompletableFuture.completedFuture(responseDetails))
        //when
        val actualResult = gitHubReposService.processRequest(requestBody)
        //then
        assertEquals(HttpStatus.NOT_FOUND,actualResult.statusCode)
    }
}