package br.com.lsm.androidsample.search

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.core.BaseAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class SearchRepositoriesActivityTest : BaseAndroidTest() {

    private val pageSize = 30

    @get:Rule
    var activityRule: ActivityTestRule<SearchRepositoriesActivity> =
        ActivityTestRule(SearchRepositoriesActivity::class.java)

    @Test
    fun shouldShowLoadingOnLastPageItem() {
        onView(withId(R.id.rvRepositories)).perform(
            RecyclerViewActions.scrollToPosition<RepositoryAdapter.ViewHolder>(pageSize -1)
        )
        onView(withId(R.id.rvRepositories)).check(matches(hasDescendant(withId(R.id.shimmerView))))
    }
}