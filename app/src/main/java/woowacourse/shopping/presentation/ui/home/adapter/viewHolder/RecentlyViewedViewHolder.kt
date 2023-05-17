package woowacourse.shopping.presentation.ui.home.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentlyViewedBinding
import woowacourse.shopping.presentation.ui.home.adapter.ClickListenerByViewType
import woowacourse.shopping.presentation.ui.home.adapter.HomeAdapter.ProductsByView.RecentlyViewedProducts
import woowacourse.shopping.presentation.ui.home.adapter.RecentlyViewedProductAdapter

class RecentlyViewedViewHolder(
    private val binding: ItemRecentlyViewedBinding,
    private val onClick: ClickListenerByViewType,
) : RecyclerView.ViewHolder(binding.root) {
    private val recentlyViewedProductAdapter by lazy { RecentlyViewedProductAdapter(onClick::setClickEventOnProduct) }

    init {
        binding.rvWrapper.adapter = recentlyViewedProductAdapter
    }

    fun bind(viewItems: RecentlyViewedProducts) {
        recentlyViewedProductAdapter.fetchRecentProducts(viewItems.recentProduct)
    }

    companion object {
        fun getView(parent: ViewGroup, layoutInflater: LayoutInflater): ItemRecentlyViewedBinding =
            ItemRecentlyViewedBinding.inflate(layoutInflater, parent, false)
    }
}