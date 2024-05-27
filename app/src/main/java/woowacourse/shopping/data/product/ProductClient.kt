package woowacourse.shopping.data.product

import android.util.Log
import com.bumptech.glide.Glide.init
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockWebServer
import woowacourse.shopping.model.Product
import java.net.InetAddress
import kotlin.concurrent.thread

class ProductClient(port: Int = 12345): ProductService {
    private val mockWebServer = MockWebServer()
    private val client = OkHttpClient()
    private val gson = Gson()
    private val baseUrl = "http://localhost:12345"

    init {
        thread {
            mockWebServer.dispatcher = ProductDispatcher
            mockWebServer.start(port)
        }.join()
    }

    override fun find(id: Long): Product {
        val request =
            Request.Builder()
                .url("$baseUrl/product/find?id=$id")
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<Product>() {}.type)
    }

    override fun findAll(): List<Product> {
        val request =
            Request.Builder()
                .url("$baseUrl/product")
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<Product>() {}.type)
    }

    override fun findRange(page: Int, pageSize: Int): List<Product> {
        val request =
            Request.Builder()
                .url("$baseUrl/products/findRange")
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        Log.d("hye", responseBody.toString())
        return gson.fromJson(responseBody, object : TypeToken<List<Product>>() {}.type)
    }

    override fun deleteAll() {
        val request =
            Request.Builder()
                .url("$baseUrl/product")
                .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()
        return gson.fromJson(responseBody, object : TypeToken<Product>() {}.type)
    }

    override fun shutdown() {
        thread {
            mockWebServer.shutdown()
        }.join()
    }
}

