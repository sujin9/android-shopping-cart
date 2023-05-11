package woowacourse.shopping.ui.products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemLoadMoreBinding
import woowacourse.shopping.ui.products.adapter.viewholder.LoadingMoreViewHolder

class LoadingButtonAdapter(
    private val onClick: (Int, Int) -> Unit,
) : RecyclerView.Adapter<LoadingMoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingMoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_load_more,
            parent,
            false,
        )

        return LoadingMoreViewHolder(ItemLoadMoreBinding.bind(view), onClick)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: LoadingMoreViewHolder, position: Int) {
    }
}
