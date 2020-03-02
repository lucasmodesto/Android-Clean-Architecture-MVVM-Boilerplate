package br.com.lsm.androidsample.presentation.githubList

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.presentation.core.BaseActivity
import br.com.lsm.androidsample.presentation.core.State
import kotlinx.android.synthetic.main.activity_repository_list.*

class RepositoriesListActivity : BaseActivity<RepositoriesListViewModel>() {

    private val repositories = mutableListOf<GithubRepo>()
    private val adapter: GitHubRepositoriesAdapter by lazy {
        GitHubRepositoriesAdapter(repositories) { onItemClick(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupRepositoriesRecyclerView()
        setLiveDataObserver()
        viewModel.fetchRepositories()
    }

    private fun setupRepositoriesRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        rvRepositories?.adapter = adapter
        rvRepositories?.layoutManager = linearLayoutManager
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

    private val onItemClick = { item: GithubRepo ->
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
        viewModel.repositoriesLiveData.observe(this, observer)
    }

}