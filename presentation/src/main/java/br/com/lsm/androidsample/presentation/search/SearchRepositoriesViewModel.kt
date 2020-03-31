package br.com.lsm.androidsample.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.domain.usecase.GetRepositoriesInput
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import br.com.lsm.androidsample.presentation.core.BaseViewModel
import br.com.lsm.androidsample.presentation.core.State
import br.com.lsm.androidsample.presentation.vo.LanguageViewObject
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.data.extensions.composeErrorTransformers
import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.domain.entity.PaginationData
import br.com.lsm.androidsample.presentation.extensions.defaultSchedulers
import br.com.lsm.androidsample.presentation.extensions.subscribeWithLiveDataState

class SearchRepositoriesViewModel(
    private val getRepositoriesUseCase: IGetRepositoriesUseCase
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
            .defaultSchedulers()
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

    private fun getAvailableLanguages(): List<LanguageViewObject> {
        return listOf(
            LanguageViewObject(
                language = Language.Kotlin,
                imageResId = R.drawable.ic_language_kotlin,
                displayNameResId = R.string.language_kotlin,
                isSelected = true
            ),
            LanguageViewObject(
                language = Language.Swift,
                imageResId = R.drawable.ic_language_swift,
                displayNameResId = R.string.language_swift
            ),
            LanguageViewObject(
                language = Language.Dart,
                imageResId = R.drawable.ic_language_dart,
                displayNameResId = R.string.language_dart
            ),
            LanguageViewObject(
                language = Language.Java,
                imageResId = R.drawable.ic_language_java,
                displayNameResId = R.string.language_java
            ),
            LanguageViewObject(
                language = Language.JavaScript,
                imageResId = R.drawable.ic_language_javascript,
                displayNameResId = R.string.language_javascript
            ),
            LanguageViewObject(
                language = Language.CSharp,
                imageResId = R.drawable.ic_language_c_sharp,
                displayNameResId = R.string.language_c_sharp
            ),
            LanguageViewObject(
                language = Language.Python,
                imageResId = R.drawable.ic_language_python,
                displayNameResId = R.string.language_python
            ),
            LanguageViewObject(
                language = Language.Scala,
                imageResId = R.drawable.ic_language_scala,
                displayNameResId = R.string.language_scala
            ),
            LanguageViewObject(
                language = Language.Ruby,
                imageResId = R.drawable.ic_language_ruby,
                displayNameResId = R.string.language_ruby
            )
        )
    }

}