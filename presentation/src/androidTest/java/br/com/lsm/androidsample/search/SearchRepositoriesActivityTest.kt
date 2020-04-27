package br.com.lsm.androidsample.search

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import br.com.lsm.androidsample.R
import br.com.lsm.androidsample.core.BaseAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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