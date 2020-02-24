package br.com.lsm.androidsample.presentation.githubList

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.presentation.core.BaseActivity
import br.com.lsm.androidsample.presentation.utils.EndlessRecyclerViewScrollListener
import br.com.lsm.androidsample.presentation.core.State
import kotlinx.android.synthetic.main.activity_repository_list.*

class RepositoriesListActivity : BaseActivity<RepositoriesListViewModel>() {

    private val repositories = mutableListOf<GithubRepo>()
    private var adapter: GitHubRepositoriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupRecyclerView()
        setLiveDataObserver()
        viewModel.fetchRepositories()
    }

    private fun setLiveDataObserver() {
        val observer = Observer<State<List<GithubRepo>>> { state ->

            when (state) {

                is State.Loading -> {
                    Toast.makeText(this, state.isLoading.toString(), Toast.LENGTH_LONG).show()
                }

                is State.Success -> {
                    state.data?.let { adapter?.update(it.toMutableList()) }
                }

                is State.Error -> {
                    state.error?.let {
                        this.handleError(
                            error = it,
                            retryAction = { viewModel.fetchRepositories() })
                    }
                }
            }
        }
        viewModel.repositoriesLiveData.observe(this, observer)
    }

    private fun setupRecyclerView() {
        adapter = GitHubRepositoriesAdapter(repositories) { item ->
            // TODO: detail screen
        }
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.addOnScrollListener(getEndlessRecyclerViewScrollListener(linearLayoutManager))
    }

    private fun getEndlessRecyclerViewScrollListener(layoutManager: LinearLayoutManager): EndlessRecyclerViewScrollListener {
        return object : EndlessRecyclerViewScrollListener(layoutManager) {

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.setNextPage()
                viewModel.fetchRepositories()
            }
        }
    }
}