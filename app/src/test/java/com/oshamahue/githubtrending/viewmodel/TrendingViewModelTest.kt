package com.oshamahue.githubtrending.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ch.tutteli.atrium.api.cc.en_GB.toBe
import ch.tutteli.atrium.verbs.assert
import com.oshamahue.githubtrending.api.GitHubService
import com.oshamahue.githubtrending.model.GitHubResponse
import com.oshamahue.githubtrending.model.Repo
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class TrendingViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: TrendingViewModel

    @MockK
    lateinit var gitHubService: GitHubService


    @Before
    fun initTests() {
        MockKAnnotations.init(this)
        viewModel = TrendingViewModel(gitHubService)

    }

    @Test
    fun testWeeklyTrending() = runBlocking {
        testTrending(viewModel::getWeeklyTrending, LocalDate.now().minusDays(7))
    }

    @Test
    fun testMonthlyTrending() = runBlocking {
        testTrending(viewModel::getMonthlyTrending, LocalDate.now().minusMonths(1))
    }

    @Test
    fun testYearlyTrending() = runBlocking {
        testTrending(viewModel::getYearlyTrending, LocalDate.now().minusMonths(12))
    }

    private fun testTrending(funToTest: () -> Unit, date: LocalDate) {
        val progressObserver: Observer<Boolean> = mockk(relaxUnitFun = true)
        viewModel.progressLiveData.observeForever(progressObserver)
        val mockResponse: GitHubResponse = mockk(relaxUnitFun = true)
        val mockList: List<Repo> = mockk(relaxUnitFun = true)

        val query = getTrendingQuery(date)
        coEvery {
            gitHubService.getTrending("stars", "desc", query).await()
        } returns mockResponse
        every { mockResponse.items } returns mockList
        funToTest()
        verify(exactly = 1) { progressObserver.onChanged(true) }
        verify(exactly = 1) { progressObserver.onChanged(false) }
        coVerify { gitHubService.getTrending("stars", "desc", query).await() }
        viewModel.githubTrendingLiveData.observeForever {
            assert(it).toBe(mockList)
        }
    }

    private fun getTrendingQuery(date: LocalDate) =
        "android+created:>${date.format(DateTimeFormatter.ISO_DATE)}"


    @Test
    fun openRepoDetail() {
        val repo: Repo = mockk()
        viewModel.openRepoDetail(repo)
        viewModel.repoDetailLiveData.observeForever {
            assert(it).toBe(repo)
        }
    }
}