package woowacourse.shopping.ui.products

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.database.product.ProductRepositoryImpl
import woowacourse.shopping.database.recentlyviewedproduct.RecentlyViewedProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductListBinding
import woowacourse.shopping.ui.cart.CartActivity
import woowacourse.shopping.ui.productdetail.ProductDetailActivity
import woowacourse.shopping.ui.products.adapter.LoadingButtonAdapter
import woowacourse.shopping.ui.products.adapter.ProductListAdapter

class ProductListActivity : AppCompatActivity(), ProductListContract.View {

    private lateinit var binding: ActivityProductListBinding
    private val presenter: ProductListContract.Presenter by lazy {
        ProductListPresenter(
            this,
            RecentlyViewedProductRepositoryImpl(this, ProductRepositoryImpl),
            ProductRepositoryImpl,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        initProductList()
    }

    override fun onResume() {
        super.onResume()
        initRecentlyViewedProductList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                moveToCartActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbarProductList)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initRecentlyViewedProductList() {
        presenter.loadRecentlyViewedProducts()
    }

    private fun initProductList() {
        presenter.loadProducts()
    }

    override fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>) {
        /*if (recentlyViewedProducts.isEmpty()) {
            binding.layoutRecentlyViewed.isVisible = false
            return
        }

        binding.recyclerViewRecentlyViewed.adapter =
            RecentlyViewedProductListAdapter(recentlyViewedProducts) {
                moveToProductDetailActivity(recentlyViewedProducts[it].id)
            }*/
    }

    override fun setProducts(products: List<ProductUIState>) {
        val concatAdapter = ConcatAdapter()

        val productListAdapter = ProductListAdapter(products) {
            presenter.addRecentlyViewedProduct(products[it].id)
            moveToProductDetailActivity(products[it].id)
        }
        concatAdapter.addAdapter(productListAdapter)

        val loadingButtonAdapter = LoadingButtonAdapter { offset, limit ->
            presenter.loadProducts(offset, limit)
        }
        concatAdapter.addAdapter(loadingButtonAdapter)

        binding.recyclerViewMainProduct.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewMainProduct.adapter = concatAdapter
    }

    override fun addProducts(products: List<ProductUIState>) {
    }

    private fun moveToProductDetailActivity(productId: Long) {
        ProductDetailActivity.startActivity(this, productId)
    }

    private fun moveToCartActivity() {
        CartActivity.startActivity(this)
    }
}
