package br.com.lsm.androidsample.domain.usecase

import br.com.lsm.androidsample.domain.entity.GithubRepository
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import io.reactivex.Observable

class GetRepositoriesUseCase(private val repository: IGitHubRepository) : IGetRepositoriesUseCase {

    override fun execute(params: GetRepositoriesInput): Observable<List<GithubRepository>> {
        return repository.getRepositories(
            language = params.language,
            sort = params.sort,
            page = params.page
        )
    }
}