package br.com.lsm.androidsample.presentation.githubList

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.presentation.core.BaseActivity
import br.com.lsm.androidsample.presentation.core.State
import kotlinx.android.synthetic.main.activity_repository_list.*
import kotlinx.android.synthetic.main.view_repositories_loading.*

class RepositoriesListActivity : BaseActivity<RepositoriesListViewModel>() {

    private val adapter: GitHubRepositoriesAdapter by lazy {
        GitHubRepositoriesAdapter(data = viewModel.repositoriesList, itemClick = {
            // TODO: detail screen
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupRepositoriesRecyclerView()
        setupLanguagesRecyclerView()
        setLiveDataObserver()
        if (viewModel.repositoriesList.isEmpty()) {
            viewModel.fetchRepositories()
        }
    }

    private fun setLiveDataObserver() {
        val observer = Observer<State<List<GithubRepo>>> { state ->
            when (state) {
                is State.Loading -> {
                    if (state.isLoading) {
                        if (viewModel.repositoriesList.isEmpty()) {
                            shimmerView?.visibility = View.VISIBLE
                            shimmerView?.startShimmer()
                        }
                    } else {
                        shimmerView?.visibility = View.GONE
                        shimmerView?.stopShimmer()
                    }
                }
                is State.Success -> {
                    adapter.update(state.data)
                }
                is State.Error -> {
                    showErrorMessage(
                        message = getString(state.getErrorMessage()),
                        action = {
                            viewModel.fetchRepositories()
                        })
                }
            }
        }
        viewModel.getRepositories().observe(this, observer)
    }

    private fun setupLanguagesRecyclerView() {
        rvLanguages?.adapter =
            LanguagesAdapter(data = viewModel.getAvailableLanguages(), onItemClick = {
                adapter.clear()
                viewModel.apply {
                    resetPage()
                    setLanguageFilter(language = it.language)
                    fetchRepositories()
                }
            })
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
}