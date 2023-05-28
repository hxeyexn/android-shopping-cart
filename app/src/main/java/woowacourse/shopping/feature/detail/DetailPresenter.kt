package woowacourse.shopping.feature.detail

import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.mapper.toDomain
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel
import java.time.LocalDateTime

class DetailPresenter(
    val view: DetailContract.View,
    private val recentProductRepository: RecentProductRepository,
    private var product: ProductUiModel,
    recentProductUiModel: RecentProductUiModel?
) : DetailContract.Presenter {
    private var recentProduct: RecentProductUiModel? = recentProductUiModel

    private val isRecentProduct: Boolean = recentProduct?.let {
        if (product.id == it.productUiModel.id) return@let true
        return@let false
    } ?: false

    override fun initScreen() {
        if (isRecentProduct || recentProduct == null) return view.hideRecentScreen()
        recentProduct?.let {
            view.setRecentScreen(
                it.productUiModel.name,
                it.productUiModel.toMoneyFormat()
            )
        }
    }

    override fun updateProductCount(count: Int) {
        if (count < 0) return
        product.count = count
    }

    override fun handleAddCartSlide() {
        view.showSelectCartProductCountScreen(product)
    }

    override fun navigateRecentProductDetail() {
        recentProduct?.let {
            recentProductRepository.addRecentProduct(
                it.toDomain().copy(dateTime = LocalDateTime.now())
            )
            view.showRecentProductDetailScreen(it)
        }
    }

    override fun setProductCountInfo(count: Int) {
        product = product.copy(count = count)
    }

    override fun exit() {
        view.exitDetailScreen()
    }
}