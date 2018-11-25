package com.oshamahue.githubtrending.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.oshamahue.githubtrending.R
import com.oshamahue.githubtrending.model.Repo
import com.oshamahue.githubtrending.viewmodel.TrendingViewModel
import kotlinx.android.synthetic.main.fragment_trading_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TrendingListFragment : Fragment() {

    private val viewModel: TrendingViewModel by sharedViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_trading_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            val adapter = RepoListAdapter(this::onRepoClick)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = adapter
            viewModel.githubTrendingLiveData.observe(this, Observer {
                adapter.submitList(it)
            })

            weeklyRb.setOnCheckedChangeListener(this::onTabClick)
            monthlyRb.setOnCheckedChangeListener(this::onTabClick)
            yearlyRb.setOnCheckedChangeListener(this::onTabClick)
            weeklyRb.isChecked = true

        }

    }

    private fun onTabClick(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked && buttonView.isPressed) {
            when (buttonView.id) {
                R.id.weeklyRb -> {
                    viewModel.getWeeklyTrending()
                }
                R.id.monthlyRb -> {
                    viewModel.getMonthlyTrending()
                }
                R.id.yearlyRb -> {
                    viewModel.getYearlyTrending()
                }
            }
        }

    }

    private fun onRepoClick(repo: Repo) {
        viewModel.openRepoDetail(repo)
        findNavController().navigate(R.id.action_trendingListFragment_to_repoDetailFragment)
    }
}