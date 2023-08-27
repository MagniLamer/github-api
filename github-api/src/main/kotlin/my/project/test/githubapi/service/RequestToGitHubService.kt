package my.project.test.githubapi.service

import my.project.test.githubapi.webClient.ResponseDetails
import my.project.test.githubapi.webClient.WebClientService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.logging.Level
import java.util.logging.Logger

private const val BASE_API = "https://api.github.com"

@Service
class RequestToGitHubService(
    private val webClientService: WebClientService
) {
    private val logger = Logger.getLogger(GitHubReposService::class.java.name)

    fun process(api: String): CompletableFuture<ResponseDetails> {
        val api = BASE_API.plus(api)
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        logger.log(
            Level.INFO,
            """
             Perform the request:
                | to api url: $api
                | with headers: $headers
                """.trimMargin()
        )
        return webClientService.performGetRequest(api, headers)
    }
}