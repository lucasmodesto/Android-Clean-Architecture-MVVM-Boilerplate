package br.com.lsm.androidsample.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.domain.entity.GithubRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_github_repository.view.*
import kotlinx.android.synthetic.main.item_load_progress.view.*

class GitHubRepositoriesAdapter(
    private val data: MutableList<GithubRepository>,
    private val itemClick: (GithubRepository) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOAD = 1
    }

    private var loadedAll = false

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (position < itemCount - 1 && !loadedAll) {
            true -> {
                val viewHolder = holder as ViewHolder
                viewHolder.bind(data[position])
            }
            false -> {
                val viewHolder = holder as ProgressViewHolder
                viewHolder.bind()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =

        when (viewType) {

            VIEW_TYPE_ITEM -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_github_repository,
                    parent, false
                ), itemClick
            )
            VIEW_TYPE_LOAD -> ProgressViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_load_progress,
                    parent,
                    false
                )
            )

            else -> ProgressViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_load_progress,
                    parent,
                    false
                )
            )
        }

    override fun getItemViewType(position: Int) =
        when (position < itemCount - 1) {
            true -> VIEW_TYPE_ITEM
            false -> VIEW_TYPE_LOAD
        }

    fun update(list: MutableList<GithubRepository>) {
        this.data.addAll(list)
        notifyItemRangeChanged(itemCount, this.data.size)
    }

    class ViewHolder(itemView: View, val itemClick: (GithubRepository) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: GithubRepository) {

            with(itemView) {
                txtRepositoryName?.text = item.name
                txtRepositoryDescription?.text = item.description
                txtForkQuantity?.text = item.forks.toString()
                txtStarQuantity?.text = item.stars.toString()
                txtOwnerFullName?.text = item.owner.name
                txtOwnerUsername?.text = item.owner.username
                Picasso.get().load(item.owner.avatarUrl).into(imgOwner)
                setOnClickListener { itemClick(item) }
            }
        }
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            itemView.progress.animate()
        }
    }
}
