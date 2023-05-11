package woowacourse.shopping.ui.products

interface ProductListContract {
    interface Presenter {
        fun loadRecentlyViewedProducts()
        fun loadProducts()

        fun loadProducts(offset: Int, limit: Int)
        fun addRecentlyViewedProduct(productId: Long)
    }

    interface View {
        fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>)
        fun setProducts(products: List<ProductUIState>)
        fun addProducts(products: List<ProductUIState>)
    }
}
