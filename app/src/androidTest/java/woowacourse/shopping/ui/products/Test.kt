package woowacourse.shopping.ui.products

import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.database.ProductDBHelper
import woowacourse.shopping.ui.productdetail.ProductDetailActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class Test {
    private lateinit var database: SQLiteDatabase

    @Before
    fun setup() {
        database =
            ProductDBHelper(ApplicationProvider.getApplicationContext()).writableDatabase
        database.delete("recently_viewed_product", null, null)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun 앱_첫_실행_후_전체_상품_리사이클러뷰가_보인다() {
        ActivityScenario.launch(ProductListActivity::class.java).use {
            onView(withId(R.id.recycler_view_main_product))
                .check(matches(isDisplayed()))
        }
    }

/*    @Test
    fun 최근_본_상품_리사이클러뷰가_보이지_않는다_does_not_exist() { // FAIL!
        ActivityScenario.launch(ProductListActivity::class.java).use {
            onView(withId(R.id.recycler_view_recently_viewed))
                .check(doesNotExist())
        }
    }

    @Test
    fun 최근_본_상품_리사이클러뷰가_보이지_않는다_not_is_displayed() {
        ActivityScenario.launch(ProductListActivity::class.java).use {
            onView(withId(R.id.recycler_view_recently_viewed))
                .check(matches(not(isDisplayed())))
        }
    }

    @Test
    fun 최근_본_상품_리사이클러뷰가_보이지_않는다_with_effective_visibility() {
        ActivityScenario.launch(ProductListActivity::class.java).use {
            onView(withId(R.id.recycler_view_recently_viewed)).check(
                matches(
                    withEffectiveVisibility(
                        ViewMatchers.Visibility.GONE,
                    ),
                ),
            )
        }
    }*/

    @Test
    fun 앱_첫_실행_후_상품_한_개를_보면_최근_본_상품_리사이클러뷰가_보인다() {
        ActivityScenario.launch(ProductListActivity::class.java).use {
            // 처음 앱이 실행되면 `최근 본 상품 리사이클러뷰`가 보이지 않는다
            onView(withId(R.id.layout_recently_viewed))
                .check(matches(not(isDisplayed())))

            // 상품을 클릭한다 (상품 상세 페이지 이동)
            onView(withId(R.id.recycler_view_main_product))
                .perform(
                    actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        1,
                        click(),
                    ),
                )

            it.moveToState(Lifecycle.State.DESTROYED)
        }

        // 상세 페이지에서 닫기 버튼을 클릭한다
        ActivityScenario.launch(ProductDetailActivity::class.java).use {
            pressBack()
            it.moveToState(Lifecycle.State.DESTROYED)
        }

        // 최근 본 항목이 보인다
        ActivityScenario.launch(ProductListActivity::class.java).use {
            onView(withId(R.id.layout_recently_viewed))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun 상품_목록_스크롤을_올리면_최근_본_상품_리사이클러뷰가_보이지_않는다() {
        상품을_클릭하면(1)

        ActivityScenario.launch(ProductListActivity::class.java).use {
            // 최근 본 항목이 보이고
            onView(withId(R.id.layout_recently_viewed))
                .check(matches(isDisplayed()))

            // 상품 리스트의 스크롤을 내리면
            onView(withId(R.id.sv_products))
                .perform(swipeUp())

            // 최근 본 상품 리사이클러뷰가 보이지 않는다
            onView(withId(R.id.recycler_view_recently_viewed))
                .check(matches(not(isDisplayed())))
        }
    }

    private fun 상품을_클릭하면(상품_인덱스: Int) {
        ActivityScenario.launch(ProductListActivity::class.java).use {
            // 상품을 클릭한다 (상품 상세 페이지 이동)
            onView(withId(R.id.recycler_view_main_product))
                .perform(
                    actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        상품_인덱스,
                        click(),
                    ),
                )

            it.moveToState(Lifecycle.State.DESTROYED)
        }

        // 상세 페이지에서 닫기 버튼을 클릭한다
        ActivityScenario.launch(ProductDetailActivity::class.java).use {
            pressBack()
            it.moveToState(Lifecycle.State.DESTROYED)
        }
    }
}
