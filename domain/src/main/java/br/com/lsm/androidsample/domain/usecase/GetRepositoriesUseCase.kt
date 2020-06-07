package br.com.lsm.androidsample.domain.usecase

import br.com.lsm.androidsample.domain.repository.IGitHubRepository

class GetRepositoriesUseCase(private val repository: IGitHubRepository) : IGetRepositoriesUseCase {

    override fun execute(params: GetRepositoriesInput) = repository.getRepositories(
        language = params.language,
        paginationCursor = params.paginationCursor
    )
}