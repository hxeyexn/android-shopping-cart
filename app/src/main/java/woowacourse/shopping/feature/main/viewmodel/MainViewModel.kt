package woowacourse.shopping.feature.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.model.Product

class MainViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun loadPage(
        page: Int,
        pageSize: Int,
    ) {
        _products.value = productRepository.findRange(page, pageSize)
    }
}