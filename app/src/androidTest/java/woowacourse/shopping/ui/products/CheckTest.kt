package woowacourse.shopping.ui.products

import android.database.sqlite.SQLiteDatabase
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.database.ProductDBHelper

class Database(
    private val database: SQLiteDatabase,
) {
    fun count(): Int {
        val cursor = database.query("recently_viewed_product", null, null, null, null, null, null)
        val count = cursor.count
        cursor.close()
        return count
    }

    fun deleteAll() {
        database.delete("recently_viewed_product", null, null)
    }
}

@RunWith(AndroidJUnit4::class)
class CheckTest {
    // Thanks to Jason !

    @get:Rule
    val activityRule = ActivityScenarioRule(ProductListActivity::class.java)

    private lateinit var database: Database

    @Before
    fun setUp() {
        database =
            Database(ProductDBHelper(ApplicationProvider.getApplicationContext()).writableDatabase)
    }

    @Test
    fun 최근_본_상품_리사이클러뷰가_보이지_않는다() {
        database.deleteAll()
        ActivityScenario.launch(ProductListActivity::class.java).use {
            it.onActivity { activity ->
                assertEquals(
                    View.GONE,
                    activity.findViewById<View>(R.id.layout_recently_viewed).visibility,
                )
            }
        }
    }

    @Test
    fun test() {
        database.deleteAll()

        ActivityScenario.launch(ProductListActivity::class.java).use {
            it.onActivity { activity ->
                // 첫 실행 시에는 `최근 본 상품 리사이클러뷰`가 보이지 않는다.
                assertEquals(
                    View.GONE,
                    activity.findViewById<View>(R.id.layout_recently_viewed).visibility,
                )

                // 상품 클릭 후 돌아온다.
                onView(withId(R.id.recycler_view_main_product))
                    .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
                onView(ViewMatchers.isRoot()).perform(pressBack())
            }
        }
    }

    @Test
    fun 상품_한_개를_보면_최근_본_상품에_추가된다() {
        println("##### 1 database.count() = ${database.count()}")

        database.deleteAll()
        println("##### 2 database.count() = ${database.count()}")

        onView(withId(R.id.layout_recently_viewed))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.recycler_view_main_product))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
            .perform(waitFor(5000))

        println("##### 3 database.count() = ${database.count()}")
        assertEquals(1, database.count())

        onView(withId(R.id.layout_recently_viewed))
            .check(matches(isDisplayed()))
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints() = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
