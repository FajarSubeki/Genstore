package genstore.id.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import genstore.id.data.repository.ProductRepositoryImpl
import genstore.id.domain.model.Product
import genstore.id.util.Constants
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _errorMessage = MutableStateFlow<String?>(null)

    val errorMessage: StateFlow<String?> = _errorMessage
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredProducts: StateFlow<List<Product>> = combine(_products, _searchQuery) { list, query ->
        if (query.isBlank()) list
        else list.filter { product ->
            val q = query.lowercase()
            (product.title?.lowercase()?.contains(q) == true) ||
                    (product.category?.lowercase()?.contains(q) == true) ||
                    (product.price?.toString()?.contains(q) == true)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val isEmpty: StateFlow<Boolean> = filteredProducts.map { it.isEmpty() }
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    private var currentPage = 0
    private val limit = 10

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                repository.fetchAndCacheProducts(currentPage, limit)
                val list = repository.getProducts(currentPage, limit).first()
                _products.update { it + list }
                currentPage++
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: Constants.UNKNOWN_ERROR
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}
