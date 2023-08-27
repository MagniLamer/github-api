package my.project.test.githubapi.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import my.project.test.githubapi.model.BranchesAndCommitsItem
import my.project.test.githubapi.model.Consumer
import my.project.test.githubapi.model.FinalResponse
import my.project.test.githubapi.model.GitHubResponseItem
import my.project.test.githubapi.webClient.ResponseDetails
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.lang.String.format
import java.util.logging.Level
import java.util.logging.Logger

private const val REPO_GETTING = "/users/%s/repos"
private const val BRANCHES_POSTFIX = "/repos/%s/%s/branches"

/**
 *Makes the response
 */
@Service
class GitHubReposService(
    private val customObjectMapper: ObjectMapper,
    private val requestToGitHubService: RequestToGitHubService
) {
    private val logger = Logger.getLogger(GitHubReposService::class.java.name)

    /**
     * Gets the response from the @RequestToGitHubService
     */
    fun processRequest(requestBody: String): ResponseEntity<String> {
        val consumerName = customObjectMapper.readValue(requestBody, Consumer::class.java).name
        val repositoriesPostfix = format(REPO_GETTING, consumerName)
        val response = requestToGitHubService.process(repositoriesPostfix).get()

        if (response.response.statusCode == HttpStatus.NOT_FOUND) {
            return processNotFoundResult(consumerName, response)
        }
        val repositories: List<GitHubResponseItem> = try {
            customObjectMapper.readValue(
                response.response.body,
                object : TypeReference<List<GitHubResponseItem>>() {})
        } catch (ex: Exception) {
            logger.log(Level.WARNING, ex.message)
            emptyList()
        }

        val finalResponse = buildFinalResponse(repositories, consumerName)
        return ResponseEntity<String>(
            customObjectMapper.writeValueAsString(finalResponse),
            HttpStatus.OK
        )
    }

    private fun buildFinalResponse(
        repositories: List<GitHubResponseItem>,
        userName: String
    ): MutableList<GitHubResponseItem> {
        val finalResponse = mutableListOf<GitHubResponseItem>()
        for (repository in repositories) {
            val branchesPostfix = format(BRANCHES_POSTFIX, userName, repository.name)
            val branchesResponse = requestToGitHubService.process(branchesPostfix).get()
            val branches: List<BranchesAndCommitsItem> =
                try {
                    customObjectMapper.readValue(
                        branchesResponse.response.body, object : TypeReference<List<BranchesAndCommitsItem>>() {})
                } catch (ex: Exception) {
                    logger.log(Level.WARNING, ex.message)
                    emptyList()
                }
            finalResponse.add(
                GitHubResponseItem.newBuilder()
                    .name(repository.name)
                    .owner(repository.owner)
                    .branchesAndCommits(branches)
                    .build()
            )
        }
        return finalResponse
    }

    private fun processNotFoundResult(
        userName: String,
        response: ResponseDetails
    ): ResponseEntity<String> {
        val finalResponse = customObjectMapper.writeValueAsString(
            FinalResponse(
                "Repository with name $userName does not exist",
                response.response.statusCode.toString()
            )
        )
        return ResponseEntity<String>(finalResponse, response.response.statusCode)
    }

    /**
     * Handles the request with invalid content type
     */
    fun handleInvalidContentType(): ResponseEntity<String> {
        val statusCode = HttpStatusCode.valueOf(406)
        val finalResponse = customObjectMapper.writeValueAsString(
            FinalResponse(
                "Please use a correct content type.",
                statusCode.toString()
            )
        )
        return ResponseEntity<String>(finalResponse, statusCode)
    }


}