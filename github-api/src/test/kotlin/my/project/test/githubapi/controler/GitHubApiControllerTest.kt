package my.project.test.githubapi.controler

import my.project.test.githubapi.service.GitHubReposService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

internal class GitHubApiControllerTest {
    private lateinit var reposService: GitHubReposService
    private lateinit var gitHubApiController: GitHubApiController

    @BeforeEach
    fun setUp() {
        reposService = mock()
        gitHubApiController = GitHubApiController(reposService)
    }

    @Test
    fun `should call the repo service`() {
        //given
        val requestBody = "requestBody"
        //when
        gitHubApiController.getUserRepos(requestBody)
        //then
        verify(reposService).processRequest(requestBody)
    }

    @Test
    fun `should handle the invalid content type`() {
        //given
        //when
        gitHubApiController.handleInvalidContentType()
        //then
        verify(reposService).handleInvalidContentType()
    }
}