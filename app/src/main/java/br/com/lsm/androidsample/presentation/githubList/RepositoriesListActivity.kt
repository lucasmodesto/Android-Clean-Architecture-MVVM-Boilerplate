package br.com.lsm.androidsample.presentation.githubList

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Language
import br.com.lsm.androidsample.presentation.core.BaseActivity
import br.com.lsm.androidsample.presentation.core.State
import br.com.lsm.androidsample.presentation.vo.LanguageViewObject
import kotlinx.android.synthetic.main.activity_repository_list.*

class RepositoriesListActivity : BaseActivity<RepositoriesListViewModel>() {

    private val repositories = mutableListOf<GithubRepo>()
    private val adapter: GitHubRepositoriesAdapter by lazy {
        GitHubRepositoriesAdapter(repositories) { onRepositoryItemClick(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupRepositoriesRecyclerView()
        setupLanguagesRecyclerView()
        setLiveDataObserver()
        if (repositories.isEmpty()) viewModel.fetchRepositories()
    }

    private fun setupLanguagesRecyclerView() {
        rvLanguages?.adapter = LanguagesAdapter(
            listOf(
                LanguageViewObject(
                    language = Language.Kotlin,
                    imageResId = R.drawable.ic_language_kotlin,
                    displayNameResId = R.string.language_kotlin
                ),
                LanguageViewObject(
                    language = Language.Swift,
                    imageResId = R.drawable.ic_language_swift,
                    displayNameResId = R.string.language_swift
                ),
                LanguageViewObject(
                    language = Language.Java,
                    imageResId = R.drawable.ic_language_java,
                    displayNameResId = R.string.language_java
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
                )
            ), onItemClick = {
                adapter.clear()
                viewModel.apply {
                    resetPage()
                    setLanguageFilter(language = it.language)
                    fetchRepositories()
                }
            }
        )
        rvLanguages?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupRepositoriesRecyclerView() {
        rvRepositories?.adapter = adapter
        rvRepositories?.layoutManager = LinearLayoutManager(this)
        rvRepositories?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) &&
                    newState == RecyclerView.SCROLL_STATE_IDLE
                ) {
                    viewModel.setNextPage()
                    viewModel.fetchRepositories()
                }
            }
        })
    }

    private val onRepositoryItemClick = { item: GithubRepo ->
        // TODO:
    }

    private fun setLiveDataObserver() {
        val observer = Observer<State<List<GithubRepo>>> { state ->
            when (state) {
                is State.Loading -> {
                    if (state.isLoading) showLoading() else hideLoading()
                }
                is State.Success -> {
                    adapter.update(state.data)
                }
                is State.Error -> {
                    handleError(
                        error = state.error,
                        retryAction = { viewModel.fetchRepositories() })
                }
            }
        }
        viewModel.liveData.observe(this, observer)
    }

}