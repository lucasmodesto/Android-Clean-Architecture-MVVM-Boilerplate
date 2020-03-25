package br.com.lsm.androidsample.domain.repository

import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.domain.entity.Language
import io.reactivex.rxjava3.core.Single

interface IGitHubRepository {

    fun getRepositories(
        language: Language,
        paginationCursor: String?
    ): Single<FetchRepositoriesResult>
}