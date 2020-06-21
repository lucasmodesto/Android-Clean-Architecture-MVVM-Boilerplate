package br.com.lsm.androidsample.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.domain.entity.GithubRepo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_github_repository.view.*
import kotlinx.android.synthetic.main.item_load_progress.view.*

class RepositoryAdapter(
    private val data: MutableList<GithubRepo>,
    private val itemClick: (GithubRepo) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOAD = 1
    }

    var hasNextPage: Boolean = true

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> (holder as? ViewHolder)?.bind(data[position])
            VIEW_TYPE_LOAD -> (holder as? ProgressViewHolder)?.bind()
        }
    }

    override fun getItemViewType(position: Int) =
        when (position < itemCount - 1) {
            true -> VIEW_TYPE_ITEM
            false -> if (hasNextPage) VIEW_TYPE_LOAD else VIEW_TYPE_ITEM
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
            else -> throw IllegalArgumentException("Invalid view type")
        }

    fun update(list: List<GithubRepo>) {
        val currentDataSize = data.size
        this.data.addAll(list)
        notifyItemRangeChanged(currentDataSize, this.data.size)
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, val itemClick: (GithubRepo) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: GithubRepo) {
            with(itemView) {
                txtRepositoryName?.text = item.name
                txtRepositoryDescription?.text = item.description
                txtForkQuantity?.text = item.forks.toString()
                txtStarQuantity?.text = item.stars.toString()
                txtOwnerUsername?.text = item.owner.username
                Picasso.get().load(item.owner.avatarUrl).into(imgOwner)
                setOnClickListener { itemClick(item) }
            }
        }
    }

    class ProgressViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            itemView.shimmerView?.startShimmer()
        }
    }
}
