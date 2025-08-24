package genstore.id.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val products: List<ProductDto>?,
    val total: Int? = null,
    val skip: Int? = null,
    val limit: Int? = null
)

@Serializable
data class ProductDto(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val category: String? = null,
    val price: Double? = null,
    val rating: Double? = null,
    val tags: List<String>? = null,
    val thumbnail: String? = null,
    val images: List<String>? = null
)
