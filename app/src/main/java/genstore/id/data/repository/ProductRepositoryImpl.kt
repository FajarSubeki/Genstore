package genstore.id.data.repository

import genstore.id.data.local.ProductDao
import genstore.id.data.local.ProductEntity
import genstore.id.data.remote.ProductApi
import genstore.id.domain.model.Product
import genstore.id.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    private val api: ProductApi,
    private val dao: ProductDao
) : ProductRepository {

    override fun getProducts(page: Int, limit: Int): Flow<List<Product>> {
        val skip = page * limit

        return dao.getProducts(limit, skip).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun fetchAndCacheProducts(page: Int, limit: Int) {
        val skip = page * limit
        val response = api.getProducts(limit, skip)

        val entities = response.products?.map {
            ProductEntity(
                id = it.id ?: 0,
                title = it.title,
                description = it.description,
                category = it.category,
                price = it.price,
                rating = it.rating,
                tags = it.tags,
                thumbnail = it.thumbnail,
                images = it.images
            )
        } ?: emptyList()

        dao.insertAll(entities)
    }
}

// Mapper
fun ProductEntity.toDomain() = Product(
    id = id,
    title = title,
    description = description,
    category = category,
    price = price,
    rating = rating,
    tags = tags,
    thumbnail = thumbnail,
    images = images
)
