package my.project.test.githubapi.model

data class GitHubResponseItem(
    val name: String? = null,
    val owner: Owner? = null,
    val fork: Boolean? = null,
    var branchesAndCommits: List<BranchesAndCommitsItem?>? = null
) {

    companion object {
        @JvmStatic
        fun newBuilder() = Builder()
    }

    data class Builder(
        private var name: String? = null,
        private var owner: Owner? = null,
        private var branchesAndCommits: List<BranchesAndCommitsItem?>? = null
    ) {
        fun name(name: String?) = apply { this.name = name }
        fun owner(owner: Owner?) = apply { this.owner = owner }
        fun branchesAndCommits(branchesAndCommits: List<BranchesAndCommitsItem?>?) =
            apply { this.branchesAndCommits = branchesAndCommits }

        fun build() = GitHubResponseItem(
            name = name,
            owner = owner,
            branchesAndCommits = branchesAndCommits
        )
    }
}

data class Owner(
    val login: String? = null
)

