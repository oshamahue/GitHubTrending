package com.oshamahue.githubtrending.com.oshamahue.githubtrending.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.oshamahue.githubtrending.R
import com.oshamahue.githubtrending.viewmodel.TrendingViewModel
import kotlinx.android.synthetic.main.fragment_repo_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RepoDetailFragment : Fragment() {

    private val viewModel: TrendingViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_repo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            viewModel.repoDetailLiveData.observe(this, Observer { repo ->
                starsTv.text = repo.starsCount.toString()
                repoTv.text = repo.fullName
                forksTv.text = Html.fromHtml(getString(R.string.forks, repo.forksCount))
                descriptionTv.text = repo.description
                languageTv.text = Html.fromHtml(getString(R.string.language, repo.language ?: ""))
                scoreTv.text = Html.fromHtml(getString(R.string.score, repo.score))
                licenseTv.text = Html.fromHtml(getString(R.string.license, repo.license?.name ?: ""))
                createdAtTv.text = Html.fromHtml(getString(R.string.createdAt, repo.createdAt))
                ownerTv.text = repo.owner.login
                if (repo.owner.avatarUrl.isBlank().not()) {
                    Glide.with(activity).asBitmap().load(repo.owner.avatarUrl).into(ownerIv)
                }
                val ownerClickListener = View.OnClickListener {
                    openUrl(repo.owner.url)
                }
                ownerTv.setOnClickListener(ownerClickListener)
                ownerIv.setOnClickListener(ownerClickListener)
                repoTv.setOnClickListener { openUrl(repo.url) }

            })


        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}