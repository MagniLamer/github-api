package my.project.test.githubapi.webClient

import org.springframework.util.MultiValueMap
import java.util.concurrent.CompletableFuture

interface WebClientService {

    fun performGetRequest(
        apiUrl: String,
        headers: MultiValueMap<String, String>?
    ): CompletableFuture<ResponseDetails>
}