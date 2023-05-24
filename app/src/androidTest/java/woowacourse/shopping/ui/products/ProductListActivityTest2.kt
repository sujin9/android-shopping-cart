package woowacourse.shopping.ui.products

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class ProductListActivityTest2 {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(ProductListActivity::class.java)

    @Before
    fun setup() {
        mActivityScenarioRule.scenario.onActivity {
            it.deleteDatabase("product.db")
        }
    }

    @Test
    fun 앱_첫_실행_후_상품_한_개를_보면_최근_본_상품_리사이클러뷰가_보인다() {
        val productRecyclerView = onView(
            allOf(
                withId(R.id.recycler_view_main_product),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1,
                ),
            ),
        )
        val recentProductsViewGroup = onView(
            allOf(
                withId(R.id.recycler_view_recently_viewed),
//                withParent(withParent(withId(R.id.sv_products))),
            ),
        )

/*        recentProductsViewGroup.check(
            matches(
                withEffectiveVisibility(
                    ViewMatchers.Visibility.GONE,
                ),
            ),
        )*/
        recentProductsViewGroup.check(
            matches(not(isDisplayed())),
        )

        productRecyclerView.perform(actionOnItemAtPosition<ViewHolder>(4, click()))
        pressBack()

        recentProductsViewGroup.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>,
        position: Int,
    ): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent) &&
                    view == parent.getChildAt(position)
            }
        }
    }
}
