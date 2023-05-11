package woowacourse.shopping.ui.products.adapter.viewholder

import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.ui.products.ProductUIState
import java.text.DecimalFormat

data class ProductListViewHolder(
    private val binding: ItemProductBinding,
    private val onClick: (Int) -> Unit,
) : CommonViewHolder(binding) {

    init {
        binding.root.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    fun bind(product: ProductUIState) {
        binding.product = product
        binding.tvProductPrice.text = itemView.context.getString(R.string.product_price)
            .format(PRICE_FORMAT.format(product.price))
        Glide.with(itemView)
            .load(product.imageUrl)
            .into(binding.ivProduct)
    }

    companion object {
        private val PRICE_FORMAT = DecimalFormat("#,###")
    }
}
