package my.project.test.githubapi.webClient

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClientRequest
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.concurrent.CompletableFuture

@Component
class WebClientServiceImpl(
    private val webClient: WebClient
) : WebClientService {

    /**
     * Performs the GET request to the GitHub API to get the repositories of the current user
     */
    override fun performGetRequest(
        apiUrl: String,
        headers: MultiValueMap<String, String>?
    ): CompletableFuture<ResponseDetails> {
        return webClient
            .get()
            .uri(apiUrl)
            .also { setRequestTimeout(it, 60_000) }
            .headers { requestHeaders ->
                if (headers != null) {
                    requestHeaders.addAll(headers)
                }
            }
            .acceptCharset(StandardCharsets.UTF_8)
            .retrieve()
            .onStatus(HttpStatus::isError) { Mono.empty() }
            .toEntity(String::class.java)
            .flatMap {
                Mono.justOrEmpty(ResponseDetails(it))
            }
            .toFuture()
    }

    private fun <S : WebClient.RequestHeadersSpec<S>> setRequestTimeout(
        requestBodySpec: WebClient.RequestHeadersSpec<S>,
        timeoutInMillis: Long?
    ) {
        if (timeoutInMillis != null && timeoutInMillis > 0) {
            requestBodySpec.httpRequest {
                it.getNativeRequest<HttpClientRequest>()
                    .responseTimeout(Duration.ofMillis(timeoutInMillis))
            }
        }
    }
}
