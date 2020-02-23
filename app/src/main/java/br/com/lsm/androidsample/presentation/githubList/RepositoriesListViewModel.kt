package br.com.lsm.androidsample.presentation.githubList

import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.usecase.GetRepositoriesInput
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import br.com.lsm.androidsample.presentation.core.BaseViewModel
import br.com.lsm.androidsample.presentation.extensions.applyDefaultSchedulers
import br.com.lsm.androidsample.presentation.core.State
import io.reactivex.rxkotlin.subscribeBy

class RepositoriesListViewModel(private val getRepositoriesUseCase: IGetRepositoriesUseCase) :
    BaseViewModel() {

    private var page: Int = 0

    val repositoriesLiveData by lazy {
        MutableLiveData<State<List<GithubRepo>>>()
    }

    fun fetchRepositories() {
        getRepositoriesUseCase.execute(
            GetRepositoriesInput(
                language = "Kotlin",
                sort = "",
                page = page
            )
        ).applyDefaultSchedulers()
            .doOnSubscribe { repositoriesLiveData.value = State.Loading(isLoading = true) }
            .subscribeBy(

                onSuccess = {
                    repositoriesLiveData.value = State.Loading(isLoading = false)
                    repositoriesLiveData.postValue(State.Success(data = it))
                },

                onError = {
                    repositoriesLiveData.value = State.Loading(isLoading = false)
                    repositoriesLiveData.postValue(State.Error(error = it))
                }
            ).also { this.disposables.add(it) }
    }

    fun setNextPage() {
        this.page++
    }

}