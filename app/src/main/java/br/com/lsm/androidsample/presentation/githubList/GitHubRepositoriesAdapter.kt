package br.com.lsm.androidsample.presentation.githubList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.domain.entity.GithubRepo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_github_repository.view.*

class GitHubRepositoriesAdapter(
    private val data: MutableList<GithubRepo>,
    private val itemClick: (GithubRepo) -> Unit
) : RecyclerView.Adapter<GitHubRepositoriesAdapter.ViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_github_repository,
                parent, false
            ), itemClick
        )

    fun update(list: List<GithubRepo>) {
        this.data.addAll(list)
        notifyItemRangeChanged(itemCount, this.data.size)
    }

    class ViewHolder(itemView: View, val itemClick: (GithubRepo) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(item: GithubRepo) {
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
}
