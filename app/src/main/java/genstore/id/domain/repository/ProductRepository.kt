package genstore.id.domain.repository

import genstore.id.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(page: Int, limit: Int): Flow<List<Product>>
}