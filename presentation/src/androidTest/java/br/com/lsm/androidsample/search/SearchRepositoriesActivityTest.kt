package br.com.lsm.androidsample.search

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import br.com.lsm.androidsample.core.BaseAndroidTest
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchRepositoriesActivityTest : BaseAndroidTest() {

    @get:Rule
    var activityRule: ActivityTestRule<SearchRepositoriesActivity> =
        ActivityTestRule(SearchRepositoriesActivity::class.java)

    // TODO: Espresso tests
}