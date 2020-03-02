package br.com.lsm.androidsample.data.repository

import br.com.lsm.androidsample.data.mapper.RepositoryMapper
import br.com.lsm.androidsample.data.model.request.LanguageQuery
import br.com.lsm.androidsample.data.network.GitHubService
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import io.reactivex.Single

class GitHubRepository(private val service: GitHubService) : IGitHubRepository {

    override fun getRepositories(
        language: Language,
        page: Int
    ): Single<List<GithubRepo>> {
        return service.getRepositories(
            query = String.format("language:%s", LanguageQuery.getValue(language)),
            page = page
        ).map { response ->
            response.items.map { RepositoryMapper.map(it) }
        }
    }

}