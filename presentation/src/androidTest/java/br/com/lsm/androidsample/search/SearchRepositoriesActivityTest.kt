package br.com.lsm.androidsample.search

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.core.BaseAndroidTest
import br.com.lsm.androidsample.core.State
import br.com.lsm.androidsample.data.errors.NetworkError
import br.com.lsm.androidsample.domain.entity.FetchRepositoriesResult
import br.com.lsm.androidsample.domain.entity.GithubRepo
import br.com.lsm.androidsample.domain.entity.Owner
import br.com.lsm.androidsample.domain.entity.PaginationData
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel

@RunWith(AndroidJUnit4::class)
class SearchRepositoriesActivityTest : BaseAndroidTest() {

    private lateinit var viewModelMock: SearchRepositoriesViewModel

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<SearchRepositoriesActivity> =
        ActivityTestRule(
            SearchRepositoriesActivity::class.java,
            true,
            false
        )

    @Before
    fun setMocks() {
        viewModelMock = mockk(relaxed = true) {
            every { repositoriesList } returns mutableListOf()
        }
        loadKoin {
            viewModel { viewModelMock }
        }
    }

    @Test
    fun shouldDisplayRepoNameOnSuccessState() {
        val livedata = MutableLiveData<State<FetchRepositoriesResult>>()
        every { viewModelMock.getRepositories() } returns livedata
        livedata.postValue(
            State.Success(data = mockk {
                every { paginationData } returns PaginationData(hasNextPage = false, endCursor = "")
                every { repositories } returns listOf(
                    GithubRepo(
                        name = "Mockk",
                        description = "Mock library for kotlin",
                        stars = 0,
                        owner = Owner(username = "", avatarUrl = "https://something.com/image"),
                        forks = 0
                    )
                )
            })
        )
        activityRule.launchActivity(null)
        onView(withText("Mockk")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowLoadingViewOnLoadingState() {
        every { viewModelMock.getRepositories() } returns
                MutableLiveData<State<FetchRepositoriesResult>>().apply {
                    postValue(State.Loading(isLoading = true))
                }
        activityRule.launchActivity(null)
        onView(withId(R.id.shimmerView)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldHideLoadingViewOnLoadingState() {
        every { viewModelMock.getRepositories() } returns
                MutableLiveData<State<FetchRepositoriesResult>>().apply {
                    postValue(State.Loading(isLoading = false))
                }
        activityRule.launchActivity(null)
        onView(withId(R.id.shimmerView)).check((matches(withEffectiveVisibility(Visibility.GONE))))
    }

    @Test
    fun shouldDisplayNoInternetMessage() {
        every { viewModelMock.getRepositories() } returns
                MutableLiveData<State<FetchRepositoriesResult>>().apply {
                    postValue(State.Error(error = NetworkError.NotConnected()))
                }
        activityRule.launchActivity(null)
        onView(withText("No internet connection")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplaySlowInternetMessage() {
        every { viewModelMock.getRepositories() } returns
                MutableLiveData<State<FetchRepositoriesResult>>().apply {
                    postValue(State.Error(error = NetworkError.SlowConnection()))
                }
        activityRule.launchActivity(null)
        onView(withText("Slow internet connection")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayUnknownErrorMessage() {
        every { viewModelMock.getRepositories() } returns
                MutableLiveData<State<FetchRepositoriesResult>>().apply {
                    postValue(State.Error(error = Exception()))
                }
        activityRule.launchActivity(null)
        onView(withText("An unexpected error has occurred")).check(matches(isDisplayed()))
    }
}