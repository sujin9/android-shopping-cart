package woowacourse.shopping.ui.products

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.database.recentlyviewedproduct.TestRecentlyProductRepositoryImpl
import woowacourse.shopping.repository.RecentlyViewedProductRepository

@RunWith(AndroidJUnit4::class)
class ProductListActivityTest {
    @get:Rule
    internal val activityRule = ActivityScenarioRule(ProductListActivity::class.java)
    private lateinit var repository: RecentlyViewedProductRepository

    @Before
    fun setUp() {
        activityRule.scenario.onActivity {
            it.deleteDatabase("product.db")
        }
        repository =
            TestRecentlyProductRepositoryImpl(ApplicationProvider.getApplicationContext())
//        repository.save(Product(1L, "", "하티", 1000))
    }

    @Test
    fun 전체_상품_리사이클러뷰가_보인다() {
        onView(withId(R.id.recycler_view_main_product))
            .check(matches(isDisplayed()))
    }

    @Test
    fun 최근_본_상품_리사이클러뷰가_보인다() {
        onView(withId(R.id.layout_recently_viewed))
            .check(matches(isDisplayed()))
    }

/*    @Test
    fun 최근_본_상품_리사이클러뷰가_보이지_않는다_does_not_exist() {
        onView(withId(R.id.recycler_view_recently_viewed))
            .check(doesNotExist())
    }*/

/*    @Test
    fun 최근_본_상품_리사이클러뷰가_보이지_않는다_not_is_displayed() {
        onView(withId(R.id.recycler_view_recently_viewed))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun 최근_본_상품_리사이클러뷰가_보이지_않는다_with_effective_visibility() {
        onView(withId(R.id.recycler_view_recently_viewed)).check(
            matches(
                withEffectiveVisibility(
                    ViewMatchers.Visibility.GONE,
                ),
            ),
        )
    }*/

    @Test
    fun 상품_한_개를_보면_최근_본_상품에_추가된다() {
//        onView(withId(R.id.layout_recently_viewed))
//            .check(matches(not(isDisplayed())))

        onView(withId(R.id.recycler_view_main_product))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
        val actual = repository.findAll()
        assertEquals(1, actual.size)

        Thread.sleep(10000)

//        onView(ViewMatchers.isRoot()).perform(pressBack())
        onView(withId(R.id.action_close))
            .perform(click())

        Thread.sleep(10000)

        onView(withId(R.id.layout_recently_viewed))
            .check(matches(isDisplayed()))
    }
}
