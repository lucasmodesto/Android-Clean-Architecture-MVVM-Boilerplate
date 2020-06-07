package br.com.lsm.androidsample.domain.repository

import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.domain.entity.Language
import kotlinx.coroutines.flow.Flow

interface IGitHubRepository {

    fun getRepositories(
        language: Language,
        paginationCursor: String?
    ): Flow<FetchRepositoriesResult>
}