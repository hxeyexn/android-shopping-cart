package woowacourse.shopping.data.cart

import woowacourse.shopping.model.CartItem

interface CartRepository {
    fun addProduct(productId: Long)

    fun deleteProduct(productId: Long)

    fun deleteCartItem(productId: Long)

    fun deleteAll()

    fun findAll(): List<CartItem>

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun count(): Int
}
