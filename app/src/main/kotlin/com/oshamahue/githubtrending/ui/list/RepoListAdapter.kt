package com.oshamahue.githubtrending.ui.list

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oshamahue.githubtrending.R
import com.oshamahue.githubtrending.model.Repo
import kotlinx.android.synthetic.main.view_repo_list_item.view.*

class RepoListAdapter(val itemClickListener: (Repo) -> Unit) : ListAdapter<Repo, RepoListAdapter.RepoViewHolder>(
    RepoDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepoViewHolder(
            inflater.inflate(
                R.layout.view_repo_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            itemClickListener(getItem(position))
        }
    }


    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(repo: Repo) {
            itemView.starsTv.text = repo.starsCount.toString()
            itemView.repoTv.text = repo.name
            itemView.forksTv.text = Html.fromHtml(itemView.context.getString(R.string.forks, repo.forksCount))
            itemView.descriptionTv.text = repo.description
            itemView.languageTv.text = Html.fromHtml(itemView.context.getString(R.string.language, repo.language ?: ""))
        }

    }
}

class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }

}