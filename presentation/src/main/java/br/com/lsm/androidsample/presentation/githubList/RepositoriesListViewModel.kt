package br.com.lsm.androidsample.presentation.githubList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.lsm.androidsample.data.extensions.composeErrorTransformers
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.domain.usecase.GetRepositoriesInput
import br.com.lsm.androidsample.domain.usecase.IGetRepositoriesUseCase
import br.com.lsm.androidsample.presentation.core.BaseViewModel
import br.com.lsm.androidsample.presentation.core.State
import br.com.lsm.androidsample.presentation.extensions.defaultSchedulers
import br.com.lsm.androidsample.presentation.extensions.subscribeWithLiveDataState
import br.com.lsm.androidsample.presentation.vo.LanguageViewObject
import br.com.lsm.androidsample.R

class RepositoriesListViewModel(private val getRepositoriesUseCase: IGetRepositoriesUseCase) :
    BaseViewModel() {

    private var page: Int = 1
    private var selectedLanguage: Language = Language.Kotlin
    private val liveData = MutableLiveData<State<List<GithubRepo>>>()
    val repositoriesList = mutableListOf<GithubRepo>()
    val languagesList = getAvailableLanguages()

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
            .subscribeWithLiveDataState(liveData)
            .also { this.disposables.add(it) }
    }

    fun setNextPage() {
        this.page++
    }

    fun resetPage() {
        this.page = 1
    }

    fun setLanguageFilter(language: Language) {
        this.selectedLanguage = language

        languagesList.forEach { it.isSelected = false }
        languagesList.find { it.language == language }?.let {
            it.isSelected = true
        }
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