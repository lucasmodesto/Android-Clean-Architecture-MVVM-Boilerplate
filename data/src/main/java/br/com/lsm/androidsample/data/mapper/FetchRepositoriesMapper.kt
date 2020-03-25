package br.com.lsm.androidsample.data.mapper

import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Owner

object FetchRepositoriesMapper {

    fun map(response: FetchRepositoriesQuery.AsRepository): GithubRepo {
        return GithubRepo(
            name = response.name,
            description = response.description.orEmpty(),
            forks = response.forkCount,
            stars = response.stargazers.totalCount,
            owner = Owner(
                avatarUrl = response.owner.avatarUrl.toString(),
                username = response.owner.login
            )
        )
    }
}