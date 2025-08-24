package genstore.id.domain.model

data class Product(
    val id: Int?,
    val title: String?,
    val description: String?,
    val category: String?,
    val price: Double?,
    val rating: Double?,
    val tags: List<String>?,
    val thumbnail: String?,
    val images: List<String>?
)

