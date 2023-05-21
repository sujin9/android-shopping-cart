package woowacourse.shopping.database

import android.provider.BaseColumns

object ProductContract {
    const val DATABASE_NAME = "product.db"
    const val DATABASE_VERSION = 3

    object CartEntry : BaseColumns {
        const val TABLE_NAME = "cart"
        const val COLUMN_NAME_PRODUCT_ID = "product_id"
    }

    object RecentlyViewedProductEntry : BaseColumns {
        const val TABLE_NAME = "recently_viewed_product"
        const val COLUMN_NAME_PRODUCT_ID = "product_id"
    }
}
