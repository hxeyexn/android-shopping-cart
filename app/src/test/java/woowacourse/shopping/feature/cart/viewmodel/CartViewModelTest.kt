package woowacourse.shopping.feature.cart.viewmodel

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import woowacourse.shopping.data.cart.CartDummyRepository
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.feature.InstantTaskExecutorExtension
import woowacourse.shopping.feature.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.model.Product
import woowacourse.shopping.price
import woowacourse.shopping.title

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private val cartRepository: CartRepository = CartDummyRepository
    private val productRepository: ProductRepository = ProductDummyRepository
    private val pageSize: Int = 5

    @BeforeEach
    fun setUp() {
        viewModel = CartViewModel(cartRepository, productRepository)
        cartRepository.deleteAll()
    }

    @Test
    fun `장바구니에 상품을 추가한다`() {
        viewModel.add(0)
        viewModel.loadCount()

        val actual = viewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(1)
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제한다`() {
        val product = productRepository.find(0L)
        viewModel.add(product.id)

        viewModel.delete(product)
        viewModel.loadCount()

        val actual = viewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `한 페이지에는 5개의 장바구니 상품이 있다`() {
        repeat(pageSize) {
            addCart(productId = it.toLong())
        }

        viewModel.loadCart(pageSize)

        val actual = viewModel.cart.getOrAwaitValue()
        assertAll(
            Executable { assertThat(actual).hasSize(pageSize) },
            Executable { assertThat(actual).isEqualTo(cartRepository.findRange(0, pageSize)) },
        )
    }

    @Test
    fun `장바구니 상품이 10개인 경우 5개의 상품을 불러온다`() {
        repeat(10) {
            addCart(productId = it.toLong())
        }

        viewModel.loadCart(pageSize)

        val actual = viewModel.cart.getOrAwaitValue()
        assertAll(
            Executable { assertThat(actual).hasSize(pageSize) },
            Executable { assertThat(actual).isEqualTo(cartRepository.findRange(0, pageSize)) },
        )
    }

    @Test
    fun `장바구니에 6개의 상품을 담았다면 장바구니 상품 수는 6이다`() {
        repeat(6) {
            addCart(productId = it.toLong())
        }

        viewModel.loadCount()

        val actual = viewModel.cartSize.getOrAwaitValue()
        assertThat(actual).isEqualTo(6)
    }

    @Test
    fun `현재 페이지의 기본 인덱스 값은 0이다`() {
        val actual = viewModel.currentPage.getOrAwaitValue()
        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `인덱스가 0인 페이지에서 페이지를 2번 증가시키면 현재 페이지의 인덱스는 2가 된다`() {
        repeat(15) {
            addCart(productId = it.toLong())
        }

        repeat(2) {
            viewModel.increasePage()
        }

        val actual = viewModel.currentPage.getOrAwaitValue()
        assertThat(actual).isEqualTo(2)
    }

    @Test
    fun `인덱스가 3인 페이지에서 페이지를 1번 감소시키면 현재 페이지의 인덱스는 2가 된다`() {
        repeat(20) {
            addCart(productId = it.toLong())
        }
        repeat(3) {
            viewModel.increasePage()
        }

        viewModel.decreasePage()

        val actual = viewModel.currentPage.getOrAwaitValue()
        assertThat(actual).isEqualTo(2)
    }

    @Test
    fun `장바구니에 담긴 상품이 5개라면 이전 페이지는 존재하지 않는다`() {
        repeat(5) {
            addCart(productId = it.toLong())
        }
        viewModel.loadCart(pageSize)

        viewModel.hasPreviousPage()

        val actual = viewModel.hasPreviousPage.getOrAwaitValue()
        assertThat(actual).isFalse()
    }

    @Test
    fun `장바구니에 담긴 상품이 8개이고, 현재 페이지의 인덱스가 1이라면 이전 페이지가 존재한다`() {
        repeat(10) {
            addCart(productId = it.toLong())
        }
        viewModel.increasePage()

        viewModel.hasPreviousPage()

        val currentPage = viewModel.currentPage.getOrAwaitValue()
        val hasPreviousPage = viewModel.hasPreviousPage.getOrAwaitValue()
        assertAll(
            Executable { assertThat(currentPage).isEqualTo(1) },
            Executable { assertThat(hasPreviousPage).isTrue() },
        )
    }

    @Test
    fun `장바구니에 담긴 상품이 5개라면 다음 페이지는 존재하지 않는다`() {
        repeat(5) {
            addCart(productId = it.toLong())
        }
        viewModel.loadCart(5)
        viewModel.loadCount()

        viewModel.hasNextPage()

        val actual = viewModel.hasNextPage.getOrAwaitValue()
        assertThat(actual).isFalse()
    }

    @Test
    fun `장바구니에 담긴 상품이 10개이고 현재 페이지의 인덱스가 0이라면 다음 페이지가 존재한다`() {
        repeat(10) {
            addCart(productId = it.toLong())
        }
        viewModel.loadCart(5)
        viewModel.loadCount()

        viewModel.hasNextPage()

        val currentPage = viewModel.currentPage.getOrAwaitValue()
        val hasNextPage = viewModel.hasNextPage.getOrAwaitValue()
        assertAll(
            Executable { assertThat(currentPage).isEqualTo(0) },
            Executable { assertThat(hasNextPage).isTrue() },
        )
    }

    @Test
    fun `장바구니에 담긴 상품이 6개일 때, 장바구니에 담긴 6번째 상품을 삭제한다면 마지막 페이지는 비어있다`() {
        repeat(6) {
            addCart(productId = it.toLong())
        }
        viewModel.increasePage()
        viewModel.delete(Product(6, imageUrl, title, price))

        viewModel.checkEmptyLastPage()

        val actual = viewModel.isEmptyLastPage.getOrAwaitValue()
        assertThat(actual).isTrue()
    }

    @Test
    fun `장바구니에 담긴 상품이 5개 라면 장바구니는 오직 한 페이지만 존재한다`() {
        repeat(5) {
            addCart(productId = it.toLong())
        }
        viewModel.loadCount()

        viewModel.checkOnlyOnePage()

        val actual = viewModel.isOnlyOnePage.getOrAwaitValue()
        assertThat(actual).isTrue()
    }

    @Test
    fun `장바구니에 담긴 상품이 6개라면 장바구니는 한 페이지만 존재하지 않는다`() {
        repeat(6) {
            addCart(productId = it.toLong())
        }
        viewModel.loadCount()

        viewModel.checkOnlyOnePage()

        val actual = viewModel.isOnlyOnePage.getOrAwaitValue()
        assertThat(actual).isFalse()
    }

    private fun addCart(productId: Long) {
        val product = productRepository.find(productId)
        cartRepository.increaseQuantity(product)
    }
}
