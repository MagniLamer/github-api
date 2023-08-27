package my.project.test.githubapi.service

import my.project.test.githubapi.webClient.WebClientService
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

internal class RequestToGitHubServiceTest {
    private lateinit var webClientService: WebClientService
    private lateinit var requestToGitHubService: RequestToGitHubService

    @BeforeEach
    fun setUp() {
        webClientService = Mockito.mock()
        requestToGitHubService = RequestToGitHubService(webClientService)
    }

    @Test
    fun `should call web client to perform get request`() {
        //given
        val api = "/apiPostfix"
        val baseApi = "https://api.github.com"
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        //when
        requestToGitHubService.process(api)
        //then
        Mockito.verify(webClientService).performGetRequest(baseApi.plus(api), headers)
    }
}