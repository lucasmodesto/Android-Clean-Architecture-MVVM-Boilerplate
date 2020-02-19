package br.com.lsm.androidsample.data.repository

import br.com.lsm.androidsample.data.mapper.RepositoryMapper
import br.com.lsm.androidsample.data.network.GitHubService
import br.com.lsm.androidsample.domain.entity.GithubRepository
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import io.reactivex.Observable

class GitHubRepository(private val service: GitHubService) : IGitHubRepository {

    override fun getRepositories(
        language: String,
        sort: String,
        page: Int
    ): Observable<List<GithubRepository>> {
        return service.getRepositories(
            query = String.format("language:%s", language),
            sort = sort,
            page = page
        ).map { response ->
            response.items.map { RepositoryMapper.map(it) }
        }
    }

}