package my.project.test.githubapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GithubApiApplication

fun main(args: Array<String>) {
	runApplication<GithubApiApplication>(*args)
}
