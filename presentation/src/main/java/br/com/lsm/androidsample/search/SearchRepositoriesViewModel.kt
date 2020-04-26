package br.com.lsm.androidsample.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.domain.usecase.GetRepositoriesInput
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import br.com.lsm.androidsample.core.BaseViewModel
import br.com.lsm.androidsample.core.State
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.data.extensions.composeErrorTransformers
import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.domain.entity.PaginationData
import br.com.lsm.androidsample.extensions.applyDefaultSchedulers
import br.com.lsm.androidsample.extensions.subscribeWithLiveDataState
import br.com.lsm.androidsample.rx.ISchedulerProvider

class SearchRepositoriesViewModel(
    private val getRepositoriesUseCase: IGetRepositoriesUseCase,
    private val schedulerProvider: ISchedulerProvider
) : BaseViewModel() {

    private var selectedLanguage: Language = Language.Kotlin
    private val liveData = MutableLiveData<State<FetchRepositoriesResult>>()
    private var paginationData: PaginationData? = null

    val repositoriesList = mutableListOf<GithubRepo>()
    val languagesList = getAvailableLanguages()

    fun getRepositories(): LiveData<State<FetchRepositoriesResult>> = liveData

    fun fetchRepositories() {
        if (paginationData?.hasNextPage == false) return
        getRepositoriesUseCase.execute(
            GetRepositoriesInput(
                language = selectedLanguage,
                paginationCursor = paginationData?.endCursor
            )
        )
            .doOnSuccess { this.paginationData = it.paginationData }
            .applyDefaultSchedulers(schedulerProvider)
            .composeErrorTransformers()
            .subscribeWithLiveDataState(liveData)
            .also { this.disposables.add(it) }
    }

    fun setLanguageFilter(language: Language) {
        this.selectedLanguage = language
        languagesList.forEach {
            it.isSelected = it.language == language
        }
    }

    fun resetPage() {
        this.paginationData = null
    }

    private fun getAvailableLanguages(): List<LanguageFilter> {
        return listOf(
            LanguageFilter(
                language = Language.Kotlin,
                imageResId = R.drawable.ic_language_kotlin,
                displayNameResId = R.string.language_kotlin,
                isSelected = true
            ),
            LanguageFilter(
                language = Language.Swift,
                imageResId = R.drawable.ic_language_swift,
                displayNameResId = R.string.language_swift
            ),
            LanguageFilter(
                language = Language.Dart,
                imageResId = R.drawable.ic_language_dart,
                displayNameResId = R.string.language_dart
            ),
            LanguageFilter(
                language = Language.Java,
                imageResId = R.drawable.ic_language_java,
                displayNameResId = R.string.language_java
            ),
            LanguageFilter(
                language = Language.JavaScript,
                imageResId = R.drawable.ic_language_javascript,
                displayNameResId = R.string.language_javascript
            ),
            LanguageFilter(
                language = Language.CSharp,
                imageResId = R.drawable.ic_language_c_sharp,
                displayNameResId = R.string.language_c_sharp
            ),
            LanguageFilter(
                language = Language.Python,
                imageResId = R.drawable.ic_language_python,
                displayNameResId = R.string.language_python
            ),
            LanguageFilter(
                language = Language.Scala,
                imageResId = R.drawable.ic_language_scala,
                displayNameResId = R.string.language_scala
            ),
            LanguageFilter(
                language = Language.Ruby,
                imageResId = R.drawable.ic_language_ruby,
                displayNameResId = R.string.language_ruby
            )
        )
    }

}