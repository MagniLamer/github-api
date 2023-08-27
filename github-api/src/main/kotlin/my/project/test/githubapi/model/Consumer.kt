package my.project.test.githubapi.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class Consumer (
	@JsonProperty("userName")
	val name: String
)

