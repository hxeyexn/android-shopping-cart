package woowacourse.shopping.feature.detail

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDummyRepository
import woowacourse.shopping.imageUrl
import woowacourse.shopping.price
import woowacourse.shopping.title

@RunWith(AndroidJUnit4::class)
class ProductDetailActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<ProductDetailActivity>

    init {
        val productId = ProductDummyRepository.save(imageUrl, title, price)
        val intent =
            Intent(ApplicationProvider.getApplicationContext(), ProductDetailActivity::class.java)
                .putExtra("product_id_key", productId)
        activityRule = ActivityScenarioRule(intent)
    }

    @Test
    fun `상품_제목이_보여진다`() {
        onView(withId(R.id.tv_product_detail_title))
            .check(matches(withText(title)))
    }

    @Test
    fun `상품_가격이_보여진다`() {
        val expected = "%,d원".format(price)
        onView(withId(R.id.tv_product_detail_price))
            .check(matches(withText(expected)))
    }

    @Test
    fun `장바구니_담기_버튼이_보여진다`() {
        onView(withId(R.id.btn_product_detail_add_cart))
            .check(matches(isDisplayed()))
    }
}