package genstore.id.domain.usecase

import genstore.id.domain.repository.ProductRepository

class GetProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(page: Int, limit: Int) = repository.getProducts(page, limit)
}