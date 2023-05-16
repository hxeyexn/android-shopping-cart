package woowacourse.shopping.database.cart

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.shopping.database.ProductContract
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository

class CartRepositoryImpl(
    private val db: SQLiteDatabase,
    private val productRepository: ProductRepository,
) : CartRepository {

    override fun findAll(): List<Product> {
        val products = mutableListOf<Product>()
        val cursor: Cursor = db.rawQuery(
            "Select * from ${ProductContract.CartEntry.TABLE_NAME}",
            null,
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            productRepository.findById(id)?.let { products.add(it) }
        }

        cursor.close()
        return products
    }

    override fun findAll(limit: Int, offset: Int): List<Product> {
        val products = mutableListOf<Product>()
        val cursor: Cursor = db.rawQuery(
            "Select * from ${ProductContract.CartEntry.TABLE_NAME} " +
                "limit $limit offset $offset",
            null,
        )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            productRepository.findById(id)?.let { products.add(it) }
        }

        cursor.close()
        return products
    }

    override fun save(product: Product) {
        val cursor =
            db.rawQuery(
                "Select * from ${ProductContract.CartEntry.TABLE_NAME} WHERE ${ProductContract.CartEntry.COLUMN_NAME_PRODUCT_ID} = ${product.id}",
                null,
            )
        if (cursor.count > 0) {
            cursor.close()
            return
        }

        val value = ContentValues().apply {
            put(ProductContract.CartEntry.COLUMN_NAME_PRODUCT_ID, product.id)
        }

        db.insert(ProductContract.CartEntry.TABLE_NAME, null, value)
        cursor.close()
    }

    override fun deleteById(productId: Long) {
        val selection = "${ProductContract.CartEntry.COLUMN_NAME_PRODUCT_ID} = ?"
        val selectionArgs = arrayOf(productId.toString())
        db.delete(ProductContract.CartEntry.TABLE_NAME, selection, selectionArgs)
    }
}