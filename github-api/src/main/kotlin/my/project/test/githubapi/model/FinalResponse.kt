package my.project.test.githubapi.model

import com.fasterxml.jackson.annotation.JsonProperty

data class FinalResponse(
	@JsonProperty("Message")
	val message: String? = null,
	val status: String? = null
)

