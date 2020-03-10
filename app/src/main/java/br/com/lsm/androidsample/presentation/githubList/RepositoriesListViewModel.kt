package br.com.lsm.androidsample.presentation.githubList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.data.extensions.composeErrorTransformers
import br.com.lsm.androidsample.data.extensions.defaultSchedulers
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.domain.usecase.GetRepositoriesInput
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import br.com.lsm.androidsample.presentation.core.BaseViewModel
import br.com.lsm.androidsample.presentation.core.State
import br.com.lsm.androidsample.presentation.vo.LanguageViewObject
import io.reactivex.rxkotlin.subscribeBy

class RepositoriesListViewModel(
    private val getRepositoriesUseCase: IGetRepositoriesUseCase
) : BaseViewModel() {

    private var page: Int = 1
    private var selectedLanguage: Language = Language.Kotlin

    private val liveData = MutableLiveData<State<List<GithubRepo>>>()

    fun getRepositories(): LiveData<State<List<GithubRepo>>> = liveData

    fun fetchRepositories() {
        getRepositoriesUseCase.execute(
            GetRepositoriesInput(
                language = selectedLanguage,
                page = page
            )
        )
            .defaultSchedulers()
            .composeErrorTransformers()
            .doOnSubscribe { liveData.value = State.Loading(isLoading = true) }
            .subscribeBy(
                onSuccess = {
                    liveData.value = State.Loading(isLoading = false)
                    liveData.value = State.Success(data = it)
                },
                onError = {
                    liveData.value = State.Loading(isLoading = false)
                    liveData.value = State.Error(error = it)
                }
            ).also { this.disposables.add(it) }
    }

    fun setNextPage() {
        this.page++
    }

    fun resetPage() {
        this.page = 1
    }

    fun setLanguageFilter(language: Language) {
        this.selectedLanguage = language
    }

}