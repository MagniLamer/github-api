package my.project.test.githubapi.controler

import my.project.test.githubapi.service.GitHubReposService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Level
import java.util.logging.Logger

/**
 *Responsible for the mapping of the request
 */
@RequestMapping(path = ["/public/api"])
@RestController
class GitHubApiController(
    private val reposService: GitHubReposService
) {
    private val logger = Logger.getLogger(GitHubReposService::class.java.name)

    /**
     * Gets the user repositories from GitHUb using the user login
     */
    @PostMapping(path = ["/repo"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserRepos(
        @RequestBody requestBody: String
    ): ResponseEntity<String> {
        logger.log(Level.INFO, "\nReceived a request with body -\n$requestBody")
        return reposService.processRequest(requestBody)
    }

    /**
     * Handles the request with invalid content type
     */
    @PostMapping(value = ["/repo"], consumes = [MediaType.APPLICATION_XML_VALUE])
    fun handleInvalidContentType(): ResponseEntity<String> {
        return reposService.handleInvalidContentType()
    }
}