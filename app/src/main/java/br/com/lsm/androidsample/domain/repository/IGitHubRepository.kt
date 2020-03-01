package br.com.lsm.androidsample.domain.repository

import br.com.lsm.androidsample.domain.entity.GithubRepo
import io.reactivex.Single

interface IGitHubRepository {

    fun getRepositories(
        language: String,
        page: Int
    ): Single<List<GithubRepo>>
}