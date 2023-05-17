package woowacourse.shopping.database.recentlyviewedproduct

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import woowacourse.shopping.database.ProductContract
import woowacourse.shopping.database.ProductDBHelper
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.RecentlyViewedProductRepository

class TestRecentlyProductRepositoryImpl(
    context: Context,
) : RecentlyViewedProductRepository {
    private val db = ProductDBHelper(context).writableDatabase

    override fun findAll(): List<Product> {
        val products = mutableListOf<Product>()
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${ProductContract.RecentlyViewedProductEntry.TABLE_NAME}",
            null,
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            ProductRepositoryImpl.findById(id)?.let { products.add(it) }
        }

        cursor.close()
        return products
    }

    override fun save(product: Product) {
        insertItem(product)
    }

    private fun insertItem(product: Product) {
        val value = ContentValues().apply {
            put(ProductContract.RecentlyViewedProductEntry.COLUMN_NAME_PRODUCT_ID, product.id)
        }
        db.insert(ProductContract.RecentlyViewedProductEntry.TABLE_NAME, null, value)
    }
}
