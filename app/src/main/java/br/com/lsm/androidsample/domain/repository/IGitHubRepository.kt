package br.com.lsm.androidsample.domain.repository

import br.com.lsm.androidsample.domain.entity.GithubRepository
import io.reactivex.Single

interface IGitHubRepository {

    fun getRepositories(
        language: String,
        sort: String,
        page: Int
    ): Single<List<GithubRepository>>
}