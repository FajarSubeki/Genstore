package genstore.id.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import genstore.id.data.repository.ProductRepositoryImpl
import genstore.id.domain.model.Product
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val isEmpty: StateFlow<Boolean> = _products.map { it.isEmpty() }
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    private var currentPage = 0
    private val limit = 10

    init {
        loadNextPage()
    }

    fun loadNextPage() {

        viewModelScope.launch {
            _isLoading.value = true

            repository.fetchAndCacheProducts(currentPage, limit)

            val list = repository.getProducts(currentPage, limit).first()
            _products.update { it + list }

            currentPage++
            _isLoading.value = false
        }

    }
}
