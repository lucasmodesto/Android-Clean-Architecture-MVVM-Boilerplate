package br.com.lsm.androidsample.domain.repository

import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Language
import io.reactivex.Single

interface IGitHubRepository {

    fun getRepositories(
        language: Language,
        page: Int
    ): Single<List<GithubRepo>>
}