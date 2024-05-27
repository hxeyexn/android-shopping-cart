package woowacourse.shopping.data.product

import android.util.Log
import com.google.gson.Gson
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import woowacourse.shopping.model.Product
import kotlin.math.min

object ProductDispatcher: Dispatcher() {
    private val gson = Gson()
    private const val SUCCESS_CODE = 200
    private const val FAILURE_CODE = 404
    private val errorResponseCode = MockResponse().setResponseCode(FAILURE_CODE)

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path ?: return errorResponseCode

        return when (request.path) {
            "/products/find" -> {
                val id = path.split("/").last().toLongOrNull() ?: return errorResponseCode
                val product = dummyProducts[id.toInt()]
                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(SUCCESS_CODE)
                    .setBody(gson.toJson(product))
            }
            "/products" -> {
                MockResponse()
                    .setHeader("Content-Type", "application/json")
                    .setResponseCode(SUCCESS_CODE)
                    .setBody(gson.toJson(dummyProducts))
            }
            "/products/findRange" -> {
//                val page = request.requestUrl?.queryParameter("page")?.toInt()
//                val pageSize = request.requestUrl?.queryParameter("page-size")?.toInt()
//                if (page != null && pageSize != null) {
                    val products = findRange(page = 0, 20)
                    Log.d("hye1", products.toString())
                    MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setResponseCode(SUCCESS_CODE)
                        .setBody(gson.toJson(products))
//                } else {
//                    errorResponseCode
//                }
            }
            else -> {
                MockResponse().setResponseCode(404)
            }
        }
    }

    fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, dummyProducts.size)
        return if (fromIndex in 0 until toIndex) {
            dummyProducts.subList(fromIndex, toIndex)
        } else {
            emptyList()
        }
    }
}