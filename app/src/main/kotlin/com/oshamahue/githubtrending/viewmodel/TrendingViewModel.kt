package com.oshamahue.githubtrending.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oshamahue.githubtrending.R
import com.oshamahue.githubtrending.api.GitHubService
import com.oshamahue.githubtrending.model.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import kotlin.coroutines.CoroutineContext


class TrendingViewModel(private val gitHubService: GitHubService) : ViewModel(), CoroutineScope {

    private val tag = "TrendingViewModel"

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    val githubTrendingLiveData = MutableLiveData<List<Repo>>()
    val progressLiveData = MutableLiveData<Boolean>()
    val toastLiveData = MutableLiveData<Int>()
    val repoDetailLiveData = MutableLiveData<Repo>()



    fun getWeeklyTrending() {
        getRepos(getTrendingQuery(LocalDate.now().minusDays(7)))
    }

    fun getMonthlyTrending() {
        getRepos(getTrendingQuery(LocalDate.now().minusMonths(1)))
    }

    fun getYearlyTrending() {
        getRepos(getTrendingQuery(LocalDate.now().minusMonths(12)))
    }

    fun openRepoDetail(repo: Repo) {
        repoDetailLiveData.postValue(repo)
    }

    private fun getRepos(
        q: String = "android",
        sort: String = "stars",
        order: String = "desc"
    ) {
        progressLiveData.postValue(true)
        launch {
            val list = try {
                gitHubService.getTrending(
                    sort,
                    order,
                    q
                ).await().items
            } catch (e: Throwable) {
                Log.w(tag, e.message, e)
                toastLiveData.postValue(R.string.generalErrorMessge)
                null
            }
            progressLiveData.postValue(false)
            list?.let {
                githubTrendingLiveData.postValue(list)
            }
        }
    }

    private fun getTrendingQuery(date: LocalDate) =
        "android+created:>${date.format(DateTimeFormatter.ISO_DATE)}"

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}