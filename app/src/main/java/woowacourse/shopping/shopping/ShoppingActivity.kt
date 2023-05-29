package woowacourse.shopping.shopping

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.R
import woowacourse.shopping.common.ProductCountClickListener
import woowacourse.shopping.databinding.ActivityShoppingBinding
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.productdetail.ProductDetailActivity
import woowacourse.shopping.productdetail.ProductDetailActivity.Companion.DETAIL_ACTIVITY_RESULT_CODE
import woowacourse.shopping.shoppingcart.ShoppingCartActivity
import woowacourse.shopping.util.generateShoppingPresenter

class ShoppingActivity : AppCompatActivity(), ShoppingContract.View {

    private lateinit var binding: ActivityShoppingBinding
    private lateinit var shoppingRecyclerAdapter: ShoppingRecyclerAdapter
    private val presenter: ShoppingContract.Presenter by lazy {
        generateShoppingPresenter(this, this)
    }
    private val shoppingDetailResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                DETAIL_ACTIVITY_RESULT_CODE -> {
                    presenter.updateRecentViewedProducts()
                }
            }
        }
    private var shoppingCartCountBadge: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shopping)

        setSupportActionBar(binding.toolbarShopping)
        presenter.loadProducts()
    }

    override fun onResume() {
        super.onResume()
        presenter.updateToolbar()
        presenter.refreshView()
    }

    override fun setUpShoppingView(
        products: List<ProductUiModel>,
        recentViewedProducts: List<ProductUiModel>,
        showMoreShoppingProducts: () -> Unit,
    ) {
        shoppingRecyclerAdapter = ShoppingRecyclerAdapter(
            products = products,
            recentViewedProducts = recentViewedProducts,
            onProductClicked = ::navigateToProductDetailView,
            onReadMoreClicked = showMoreShoppingProducts,
            countClickListener = object : ProductCountClickListener {
                override fun onPlusClick(id: Int) {
                    presenter.changeShoppingCartProductCount(id, true)
                }

                override fun onMinusClick(id: Int) {
                    presenter.changeShoppingCartProductCount(id, false)
                }
            },
        )

        with(binding) {
            productRecyclerView.layoutManager = GridLayoutManager(root.context, 2).apply {
                spanSizeLookup =
                    ShoppingRecyclerSpanSizeManager(shoppingRecyclerAdapter::getItemViewType)
            }
            productRecyclerView.adapter = shoppingRecyclerAdapter
        }
    }

    override fun refreshRecentViewedProductsView(toReplace: List<ProductUiModel>) {
        shoppingRecyclerAdapter.refreshRecentViewedItems(toReplace)
    }

    override fun refreshMoreShoppingProductsView(toAdd: List<ProductUiModel>) {
        shoppingRecyclerAdapter.readMoreShoppingItems(toAdd = toAdd)
    }

    override fun refreshShoppingProductsView(products: List<ProductUiModel>) {
        shoppingRecyclerAdapter.refreshShoppingItems(products)
    }

    private fun navigateToProductDetailView(product: ProductUiModel) {
        presenter.addToRecentViewedProduct(product.id)
        val intent = ProductDetailActivity.getIntent(this, product)

        shoppingDetailResultLauncher.launch(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shopping, menu)

        menu?.findItem(R.id.shopping_cart)?.actionView?.let { view ->
            view.setOnClickListener {
                navigateToShoppingCartView()
            }
            view.findViewById<TextView>(R.id.text_total_cart_size_count)
                ?.let { shoppingCartCountBadge = it }
        }
        presenter.updateToolbar()

        return super.onCreateOptionsMenu(menu)
    }

    private fun navigateToShoppingCartView() {
        startActivity(
            Intent(
                this,
                ShoppingCartActivity::class.java,
            ),
        )
    }

    override fun updateToolbar(count: Int) {
        if (count < MINIMUM_SIZE) {
            shoppingCartCountBadge?.visibility = View.GONE
        } else {
            shoppingCartCountBadge?.visibility = View.VISIBLE
        }
        shoppingCartCountBadge?.text = count.toString()
    }

    companion object {
        private const val MINIMUM_SIZE = 1
    }
}