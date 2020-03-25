package br.com.lsm.androidsample.domain.usecase

import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.domain.repository.IGitHubRepository
import io.reactivex.rxjava3.core.Single

class GetRepositoriesUseCase(private val repository: IGitHubRepository) : IGetRepositoriesUseCase {

    override fun execute(params: GetRepositoriesInput): Single<FetchRepositoriesResult> {
        return repository.getRepositories(
            language = params.language,
            paginationCursor = params.paginationCursor
        )
    }
}