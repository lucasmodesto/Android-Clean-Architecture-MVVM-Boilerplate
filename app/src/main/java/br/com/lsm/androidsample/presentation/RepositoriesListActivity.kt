package br.com.lsm.androidsample.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.domain.entity.GithubRepository
import br.com.lsm.androidsample.presentation.utils.EndlessRecyclerViewScrollListener
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_repository_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesListActivity : AppCompatActivity() {

    private val viewModel by viewModel<RepositoriesListViewModel>()
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

            onNext = {
                Log.e("TAG", "onSuccess")
                adapter?.update(it.toMutableList())
            },

            onError = {
                Log.e("TAG", "onError", it)
                showErrorMessage()
            }
        )
    }

    private fun setupRecyclerView() {
        adapter = GitHubRepositoriesAdapter(repositories) { item ->
            Toast.makeText(this, item.name, Toast.LENGTH_LONG).show()
        }

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = linearLayoutManager
        recyclerView?.addOnScrollListener(getEndlessRecyclerViewScrollListener(linearLayoutManager))
    }

    private fun showErrorMessage() {
        Snackbar.make(
            rootViewGroup,
            "Erro ao carregar repositórios",
            Snackbar.LENGTH_INDEFINITE
        ).setActionTextColor(ContextCompat.getColor(this, android.R.color.white))
            .setAction(getString(R.string.message_retry)) {
                loadRepositories()
            }.show()
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