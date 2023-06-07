package woowacourse.shopping.feature.cart

import com.example.data.repository.CartRepository
import com.example.domain.CartProduct
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.model.mapper.toDomain
import woowacourse.shopping.feature.model.mapper.toUi

class CartActivityPresenter(
    private val view: CartActivityContract.View,
    private val cartRepository: CartRepository,
) : CartActivityContract.Presenter {
    private var page = MIN_PAGE
    private val selectedItem: MutableList<CartProductItem> = mutableListOf()

    override fun loadInitialData() {
        val items = getItems(page)
        view.setPage(page)
        view.setUpAdapterData(items)
        view.updateButtonsEnabledState(page, getMaxPage())
    }

    override fun removeItem(item: CartProductItem) {
        selectedItem.remove(item)
        setBottomView()

        cartRepository.deleteColumn(item.toDomain())
        val items = getItems(page)
        val selectedState = getSelectedStateEachPage()
        view.updateAdapterData(items, selectedState)
    }

    override fun selectAllItems(isChecked: Boolean) {
        val items = getItems(page)
        if (isChecked) {
            selectedItem.addAll(items)
        } else {
            selectedItem.removeAll(items)
        }
        val selectedState = getSelectedStateEachPage()
        view.updateAdapterData(items, selectedState)

        setBottomView()
    }

    private fun getItems(page: Int): List<CartProductItem> {
        return cartRepository.getCartProducts(
            limit = ITEM_COUNT_EACH_PAGE,
            offset = (page - 1) * ITEM_COUNT_EACH_PAGE,
        ).map(CartProduct::toUi)
    }

    private fun getSelectedStateEachPage(): List<Boolean> {
        val items = getItems(page)
        val result = mutableListOf<Boolean>()

        items.forEach { cartProductItem ->
            result.add(selectedItem.contains(cartProductItem))
        }
        return result
    }

    override fun setUpButton() {
        val maxPage = getMaxPage()
        view.setButtonClickListener(maxPage)
    }

    private fun isAllSelected(): Boolean {
        val items = getItems(page)
        return selectedItem.containsAll(items)
    }

    private fun getMaxPage(): Int {
        val entireData = cartRepository.getAll()
        if (entireData.isEmpty()) return MIN_PAGE
        return (entireData.size - 1) / ITEM_COUNT_EACH_PAGE + 1
    }

    override fun onNextPage() {
        val maxPage = getMaxPage()
        if (page < maxPage) {
            ++page
        }
        updateData()
    }

    override fun onPreviousPage() {
        if (page > MIN_PAGE) {
            --page
        }
        updateData()
    }

    private fun updateData() {
        val items = getItems(page)
        view.setPage(page)
        val selected = getSelectedStateEachPage()
        view.updateAdapterData(items, selected)
        view.updateButtonsEnabledState(page, getMaxPage())
        view.setAllSelected(isAllSelected())
    }

    override fun updateItem(item: CartProductItem, isPlus: Boolean) {
        var newCartProduct: CartProduct = item.toDomain()

        if (cannotMinus(isPlus, item.count)) return
        val changeWidth = if (isPlus) 1 else -1

        if (!selectedItem.contains(item)) {
            newCartProduct = newCartProduct.updateCount(item.count + changeWidth)
            updateDbAndView(newCartProduct)
            return
        }

        selectedItem.remove(item)
        newCartProduct = newCartProduct.updateCount(item.count + changeWidth)
        selectedItem.add(newCartProduct.toUi())
        updateDbAndView(newCartProduct)
        setBottomView()
    }

    private fun updateDbAndView(item: CartProduct) {
        cartRepository.updateColumn(item)
        val items = getItems(page)
        val selected = getSelectedStateEachPage()
        view.updateAdapterData(items, selected)
    }

    private fun cannotMinus(isPlus: Boolean, oldCount: Int): Boolean {
        return !isPlus && oldCount <= MIN_COUNT
    }

    override fun toggleItemChecked(item: CartProductItem) {
        if (selectedItem.contains(item)) {
            selectedItem.remove(item)
        } else {
            selectedItem.add(item)
        }
        setBottomView()
    }

    override fun setBottomView() {
        val totalPrice = getSelectedItemTotalPrice()
        view.setPrice(totalPrice)
        view.setAllSelected(isAllSelected())
        view.setOrderNumber(selectedItem.size)
    }

    private fun getSelectedItemTotalPrice(): Int {
        return selectedItem.fold(0) { total, product ->
            total + (product.price * product.count)
        }
    }

    companion object {
        private const val ITEM_COUNT_EACH_PAGE = 5
        private const val MIN_PAGE = 1
        private const val MIN_COUNT = 1
    }
}