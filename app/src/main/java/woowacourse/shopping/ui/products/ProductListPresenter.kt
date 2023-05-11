package woowacourse.shopping.ui.products

import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.repository.RecentlyViewedProductRepository

class ProductListPresenter(
    private val view: ProductListContract.View,
    private val recentlyViewedProductRepository: RecentlyViewedProductRepository,
    private val productRepository: ProductRepository,
) : ProductListContract.Presenter {

    override fun loadRecentlyViewedProducts() {
        view.setRecentlyViewedProducts(
            recentlyViewedProductRepository.findAll().map(RecentlyViewedProductUIState::from).reversed(),
        )
    }

    override fun loadProducts() {
        view.setProducts(productRepository.findAll(20, 0).map(ProductUIState::from))
    }

    override fun loadProducts(offset: Int, limit: Int) {
        view.addProducts(productRepository.findAll(limit, offset).map(ProductUIState::from))
    }

    override fun addRecentlyViewedProduct(productId: Long) {
        productRepository.findById(productId)?.run {
            recentlyViewedProductRepository.save(this)
        }
    }
}
