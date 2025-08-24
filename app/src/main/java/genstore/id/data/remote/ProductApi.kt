package genstore.id.data.remote

import genstore.id.util.Constants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ProductApi(private val client: HttpClient) {

    suspend fun getProducts(limit: Int, skip: Int): ProductResponse {
        return client.get("${Constants.BASE_URL}${Constants.PRODUCTS_ENDPOINT}") {
            parameter(Constants.PARAM_LIMIT, limit)
            parameter(Constants.PARAM_SKIP, skip)
        }.body()
    }
}
