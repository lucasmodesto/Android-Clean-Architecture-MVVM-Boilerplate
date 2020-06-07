package br.com.lsm.androidsample.search

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.core.BaseActivity
import br.com.lsm.androidsample.core.State
import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.utils.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.activity_repository_list.*
import kotlinx.android.synthetic.main.view_repositories_loading.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchRepositoriesActivity : BaseActivity() {

    private val viewModel: SearchRepositoriesViewModel by viewModel()

    private val repositoriesAdapter: RepositoryAdapter by lazy {
        RepositoryAdapter(data = viewModel.repositoriesList, itemClick = {
            // TODO: detail screen
        })
    }

    private val languagesAdapter: LanguageFilterAdapter by lazy {
        LanguageFilterAdapter(data = viewModel.languagesList, onItemClick = {
            if (!isLoading) {
                repositoriesAdapter.clear()
                viewModel.apply {
                    resetPage()
                    setLanguageFilter(language = it.language)
                    fetchRepositories()
                }
                languagesAdapter.notifyDataSetChanged()
            }
        })
    }

    private var isLoading = false

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
        val observer = Observer<State<FetchRepositoriesResult>> { state ->
            when (state) {
                is State.Loading -> {
                    this.isLoading = state.isLoading
                    if (state.isLoading) {
                        if (viewModel.repositoriesList.isEmpty()) {
                            shimmerView?.visibility = View.VISIBLE
                        }
                    } else {
                        shimmerView?.visibility = View.GONE
                    }
                }
                is State.Success -> {
                    repositoriesAdapter.hasNextPage = state.data.paginationData.hasNextPage
                    repositoriesAdapter.update(state.data.repositories)
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
        rvLanguages?.run {
            adapter = languagesAdapter
            layoutManager =
                LinearLayoutManager(
                    this@SearchRepositoriesActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }
    }

    private fun setupRepositoriesRecyclerView() {
        rvRepositories?.run {
            adapter = repositoriesAdapter
            layoutManager = LinearLayoutManager(this@SearchRepositoriesActivity)
            addItemDecoration(VerticalSpaceItemDecoration(verticalSpaceInDp = 8))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1) &&
                        newState == RecyclerView.SCROLL_STATE_IDLE
                    ) {
                        viewModel.fetchRepositories()
                    }
                }
            })
        }
    }
}