package woowacourse.shopping.feature.product.detail

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import woowacourse.shopping.R
import woowacourse.shopping.data.datasource.cartdatasource.CartLocalDataSourceImpl
import woowacourse.shopping.data.datasource.recentproductdatasource.RecentProductLocalDataSourceImpl
import woowacourse.shopping.data.db.cart.CartDbHelper
import woowacourse.shopping.data.db.recentproduct.RecentProductDbHelper
import woowacourse.shopping.data.repository.CartRepositoryImpl
import woowacourse.shopping.data.repository.RecentProductRepositoryImpl
import woowacourse.shopping.databinding.ActivityProductDetailBinding
import woowacourse.shopping.feature.extension.showToast
import woowacourse.shopping.feature.list.item.ProductView.CartProductItem
import woowacourse.shopping.feature.product.detail.dialog.CounterDialog
import woowacourse.shopping.feature.product.detail.dialog.CounterDialog.Companion.COUNTER_DIALOG_TAG

class ProductDetailActivity : AppCompatActivity(), ProductDetailContract.View {
    private lateinit var binding: ActivityProductDetailBinding
    override lateinit var presenter: ProductDetailPresenter

    private val product: CartProductItem? by lazy { intent.getParcelableExtra(PRODUCT_KEY) }
    private val lastProduct: CartProductItem? by lazy { intent.getParcelableExtra(LAST_PRODUCT_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (product == null) {
            showToast(getString(R.string.error_intent_message))
            finish()
        }

        initPresenter()
        presenter.initScreen()
        setView()
    }

    private fun initPresenter() {
        val cartDbHelper = CartDbHelper(this)
        val cartLocalDataSource = CartLocalDataSourceImpl(cartDbHelper)
        val cartRepository = CartRepositoryImpl(cartLocalDataSource)

        val recentDbHelper = RecentProductDbHelper(this)
        val recentProductLocalDataSource = RecentProductLocalDataSourceImpl(recentDbHelper)
        val recentProductRepository = RecentProductRepositoryImpl(recentProductLocalDataSource)
        presenter = ProductDetailPresenter(
            this,
            recentProductRepository,
            cartRepository,
            product!!,
            lastProduct,
        )
    }

    private fun setView() {
        binding.product = product

        product?.let { product ->
            binding.addToCartButton.setOnClickListener {
                val counterDialog = CounterDialog.newInstance(product)
                counterDialog.show(supportFragmentManager, COUNTER_DIALOG_TAG)
            }
        }

        binding.recentInfoBoxLayout.setOnClickListener {
            presenter.navigateRecentProductDetail()
        }
    }

    override fun hideRecentProductInfoView() {
        binding.recentInfoBoxLayout.visibility = View.GONE
    }

    override fun setRecentProductInfo(name: String, price: Int) {
        binding.recentProductName.text = name
        binding.recentProductPrice.text = getString(R.string.price_format, price)
    }

    override fun showRecentProductDetail(product: CartProductItem) {
        startActivity(
            getIntent(this, product, product).apply {
                addFlags(FLAG_ACTIVITY_CLEAR_TOP)
            },
        )
    }

    companion object {
        private const val PRODUCT_KEY = "product"
        private const val LAST_PRODUCT_KEY = "last_product"

        fun getIntent(
            context: Context,
            product: CartProductItem,
            lastProduct: CartProductItem?,
        ): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_KEY, product)
                putExtra(LAST_PRODUCT_KEY, lastProduct)
            }
        }
    }
}