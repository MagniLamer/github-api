package my.project.test.githubapi.model

data class BranchesAndCommitsItem(
	val name: String? = null,
	val commit: Commit? = null
)

data class Commit(
	val sha: String? = null
)

