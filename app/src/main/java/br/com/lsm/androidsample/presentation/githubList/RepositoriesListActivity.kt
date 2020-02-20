package br.com.lsm.androidsample.presentation.githubList

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.domain.entity.GithubRepository
import br.com.lsm.androidsample.presentation.core.BaseActivity
import br.com.lsm.androidsample.presentation.utils.EndlessRecyclerViewScrollListener
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_repository_list.*

class RepositoriesListActivity : BaseActivity<RepositoriesListViewModel>() {

    private val repositories = mutableListOf<GithubRepository>()
    private var adapter: GitHubRepositoriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)
        setupRecyclerView()
        loadRepositories()
    }

    private fun loadRepositories() {
        viewModel.getRepositories().subscribeBy(

            onSuccess = {
                adapter?.update(it.toMutableList())
            },

            onError = {
                showError(message = getString(R.string.error_message_failed_repositories)) {
                    loadRepositories()
                }
            }
        )
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
                loadRepositories()
            }
        }
    }
}