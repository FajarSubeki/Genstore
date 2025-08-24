package genstore.id.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val title: String?,
    val description: String?,
    val category: String?,
    val price: Double?,
    val rating: Double?,
    val tags: List<String>?,
    val thumbnail: String?,
    val images: List<String>?
)

