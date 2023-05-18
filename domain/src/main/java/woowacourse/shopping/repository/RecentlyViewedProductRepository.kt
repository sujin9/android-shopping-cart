package woowacourse.shopping.repository

import woowacourse.shopping.domain.RecentlyViewedProduct

interface RecentlyViewedProductRepository {
    fun findAll(): List<RecentlyViewedProduct>
    fun save(product: RecentlyViewedProduct)
}
