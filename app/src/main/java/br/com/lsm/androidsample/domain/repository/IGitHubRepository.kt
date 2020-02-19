package br.com.lsm.androidsample.domain.repository

import br.com.lsm.androidsample.domain.entity.GithubRepository
import io.reactivex.Observable

interface IGitHubRepository {

    fun getRepositories(
        language: String,
        sort: String,
        page: Int
    ): Observable<List<GithubRepository>>
}