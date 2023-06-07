package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

interface ProductDetailContract {
    interface Presenter {
        fun init(productId: Long)
        fun addDialogCount(count: Int)
        fun minusDialogCount(count: Int)
        fun addProductToCart(productId: Long, count: Int)
        fun navigateToCart()
        fun navigateToProductDetail(productId: Long)
    }

    interface View {
        fun setProduct(product: ProductDetailUIState)
        fun showLastlyViewedProduct(product: RecentlyViewedProductUIState)
        fun hideLastlyViewedProduct()
        fun updateCount(count: Int)
        fun showErrorMessage()
        fun moveToCartActivity()
        fun moveToProductDetailActivity(productId: Long)
    }
}
