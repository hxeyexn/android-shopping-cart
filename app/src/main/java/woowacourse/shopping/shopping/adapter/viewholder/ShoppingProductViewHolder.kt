package woowacourse.shopping.shopping.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import woowacourse.shopping.common.CountPickerListener
import woowacourse.shopping.common.ZeroCountHandler
import woowacourse.shopping.databinding.ItemShoppingProductBinding
import woowacourse.shopping.model.CartProductUiModel

class ShoppingProductViewHolder private constructor(
    binding: ItemShoppingProductBinding,
    private val onProductImageClicked: (productId: Int) -> Unit,
    private val onAddToCartButtonClicked: (product: CartProductUiModel) -> Unit,
    private val getCountPickerListener: (product: CartProductUiModel) -> CountPickerListener,
) : ShoppingRecyclerItemViewHolder<ShoppingRecyclerItem.ShoppingProduct, ItemShoppingProductBinding>(
    binding
) {

    init {
        setOnClicked()
    }

    private fun setOnClicked() {
        with(binding) {
            imageProduct.setOnClickListener {
                onProductImageClicked(product?.id ?: return@setOnClickListener)
            }
            imageAddToCart.setOnClickListener {
                it.isVisible = false
                countPicker.isVisible = true
                onAddToCartButtonClicked(product ?: return@setOnClickListener)
            }
        }
    }

    private fun getZeroCountHandler(): ZeroCountHandler {

        return ZeroCountHandler {
            binding.imageAddToCart.isVisible = true
            binding.countPicker.isVisible = false
        }
    }

    override fun bind(
        itemData: ShoppingRecyclerItem.ShoppingProduct,
    ) {
        binding.product = itemData.value
        binding.countPicker.setListener(
            countPickerListener = getCountPickerListener(itemData.value),
            zeroCountHandler = getZeroCountHandler()
        )
        if (itemData.value.count != 0) {
            binding.countPicker.setTextCount(itemData.value.count)
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onProductImageClicked: (productId: Int) -> Unit,
            onAddToCartButtonClicked: (product: CartProductUiModel) -> Unit,
            getCountPickerListener: (product: CartProductUiModel) -> CountPickerListener,
        ): ShoppingProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemShoppingProductBinding.inflate(layoutInflater, parent, false)

            return ShoppingProductViewHolder(
                binding = binding,
                onProductImageClicked = onProductImageClicked,
                onAddToCartButtonClicked = onAddToCartButtonClicked,
                getCountPickerListener = getCountPickerListener
            )
        }
    }
}
