package woowacourse.shopping.feature.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product

class CartViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _cart = MutableLiveData<List<CartItem>>()
    val cart: LiveData<List<CartItem>> get() = _cart

    private val _cartSize = MutableLiveData<Int>()
    val cartSize: LiveData<Int> get() = _cartSize

    private val _currentPage = MutableLiveData(MIN_PAGE)
    val currentPage: LiveData<Int> = _currentPage

    private val _hasPreviousPage = MutableLiveData<Boolean>()
    val hasPreviousPage: LiveData<Boolean> = _hasPreviousPage

    private val _hasNextPage = MutableLiveData<Boolean>()
    val hasNextPage: LiveData<Boolean> = _hasNextPage

    private val _isEmptyLastPage = MutableLiveData<Boolean>()
    val isEmptyLastPage: LiveData<Boolean> = _isEmptyLastPage

    private val _isOnlyOnePage = MutableLiveData<Boolean>()
    val isOnlyOnePage: LiveData<Boolean> = _isOnlyOnePage

    init {
        updatePage()
    }

    fun add(productId: Long) {
        val product = productRepository.find(productId)
        cartRepository.increaseQuantity(product)
    }

    fun delete(product: Product) {
        cartRepository.deleteCartItem(product)
        updatePage()
    }

    fun loadCart(pageSize: Int) {
        val page = currentPage.value ?: return
        _cart.value = cartRepository.findRange(page, pageSize)
    }

    fun loadCount() {
        _cartSize.value = cartRepository.count()
    }

    fun increasePage() {
        _currentPage.value = _currentPage.value?.plus(1)
        updatePage()
    }

    fun decreasePage() {
        _currentPage.value = _currentPage.value?.minus(1)
        updatePage()
    }

    fun hasPreviousPage() {
        val page = currentPage.value ?: return
        _hasPreviousPage.value = page > MIN_PAGE
    }

    fun hasNextPage() {
        val currentPage = currentPage.value ?: return
        val cartSize = cartSize.value ?: return
        _hasNextPage.value = currentPage < (cartSize - 1) / MAX_ITEM_SIZE_PER_PAGE
    }

    fun checkEmptyLastPage() {
        val currentPage = currentPage.value ?: return
        val cartSize = cartSize.value ?: return
        _isEmptyLastPage.value = currentPage > MIN_PAGE && cartSize % MAX_ITEM_SIZE_PER_PAGE == 1
    }

    fun checkOnlyOnePage() {
        val cartSize = cartSize.value ?: return
        _isOnlyOnePage.value = cartSize <= MAX_ITEM_SIZE_PER_PAGE
    }

    private fun updatePage() {
        loadCart(MAX_ITEM_SIZE_PER_PAGE)
        loadCount()
    }

    companion object {
        private const val MIN_PAGE = 0
        private const val MAX_ITEM_SIZE_PER_PAGE = 5
    }
}
