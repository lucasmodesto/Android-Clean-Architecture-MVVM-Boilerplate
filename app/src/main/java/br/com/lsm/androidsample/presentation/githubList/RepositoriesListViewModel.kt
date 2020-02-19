package br.com.lsm.androidsample.presentation

import br.com.lsm.androidsample.domain.entity.GithubRepository
import br.com.lsm.androidsample.domain.usecase.GetRepositoriesInput
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import br.com.lsm.androidsample.presentation.core.BaseViewModel
import br.com.lsm.androidsample.presentation.extensions.applyDefaultSchedulers
import io.reactivex.Observable

class RepositoriesListViewModel(
    private val getRepositoriesUseCase: IGetRepositoriesUseCase
) : BaseViewModel() {

    private var page: Int = 0

    fun getRepositories(): Observable<List<GithubRepository>> {
        return getRepositoriesUseCase.execute(
            GetRepositoriesInput(
                language = "Kotlin",
                sort = "",
                page = page
            )
        ).doOnSubscribe {
            this.disposables.add(it)
        }.applyDefaultSchedulers()
    }

    fun setNextPage() {
        this.page++
    }

}