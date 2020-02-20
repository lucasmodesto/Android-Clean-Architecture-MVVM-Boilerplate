package br.com.lsm.androidsample.domain.usecase

import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import io.reactivex.Single

class GetRepositoriesUseCase(private val repository: IGitHubRepository) : IGetRepositoriesUseCase {

    override fun execute(params: GetRepositoriesInput): Single<List<GithubRepo>> {
        return repository.getRepositories(
            language = params.language,
            sort = params.sort,
            page = params.page
        )
    }
}