package br.com.lsm.androidsample.data.mapper

import br.com.lsm.androidsample.data.model.GitHubRepositoryResponse
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Owner

object RepositoryMapper {

    fun map(response: GitHubRepositoryResponse): GithubRepo {
        return GithubRepo(
            name = response.name,
            description = response.description.orEmpty(),
            forks = response.forksCount,
            stars = response.stargazersCount,
            owner = Owner(
                name = response.fullName.orEmpty(),
                avatarUrl = response.owner.avatarUrl.orEmpty(),
                username = response.owner.login.orEmpty()
            )
        )
    }
}