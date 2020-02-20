package br.com.lsm.androidsample.presentation.githubList

import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.usecase.GetRepositoriesInput
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import br.com.lsm.androidsample.presentation.core.BaseViewModel
import br.com.lsm.androidsample.presentation.extensions.applyDefaultSchedulers
import br.com.lsm.androidsample.presentation.utils.LiveDataState

class RepositoriesListViewModel(private val getRepositoriesUseCase: IGetRepositoriesUseCase) :
    BaseViewModel() {

    private var page: Int = 0

    val repositoriesLiveData by lazy {
        MutableLiveData<LiveDataState<List<GithubRepo>>>()
    }

    fun fetchRepositories() {
        getRepositoriesUseCase.execute(
            GetRepositoriesInput(
                language = "Kotlin",
                sort = "",
                page = page
            )
        ).doOnSubscribe {
            this.disposables.add(it)
            this.repositoriesLiveData.postValue(LiveDataState.Loading())
        }.applyDefaultSchedulers()
            .doOnSuccess { repositoriesLiveData.postValue(LiveDataState.Success(data = it)) }
            .doOnError { repositoriesLiveData.postValue(LiveDataState.Error(it)) }
            .subscribe()
    }

    fun setNextPage() {
        this.page++
    }

}