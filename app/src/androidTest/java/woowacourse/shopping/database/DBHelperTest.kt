package woowacourse.shopping.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test

class DBHelperTest {
    @Test
    fun 최근_조회한_항목_테이블이_데이터베이스에_존재하는지_확인() {
        val databaseHelper = ProductDBHelper(ApplicationProvider.getApplicationContext())
        val db: SQLiteDatabase = databaseHelper.readableDatabase

        Assert.assertTrue(db != null)

        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM recently_viewed_product",
            null,
        )
        Assert.assertTrue(cursor != null && cursor.count > 0)

        cursor.close()
        db.close()
    }

    @Test
    fun 장바구니_테이블이_데이터베이스에_존재하는지_확인() {
        val databaseHelper = ProductDBHelper(ApplicationProvider.getApplicationContext())
        val db: SQLiteDatabase = databaseHelper.readableDatabase

        Assert.assertTrue(db != null)

        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM cart",
            null,
        )
        Assert.assertTrue(cursor != null && cursor.count > 0)

        cursor.close()
        db.close()
    }
}
