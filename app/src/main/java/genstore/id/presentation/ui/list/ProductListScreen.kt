package genstore.id.presentation.ui.list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import genstore.id.domain.model.Product
import genstore.id.presentation.component.ProductCard
import genstore.id.presentation.component.SearchTextField
import genstore.id.presentation.viewmodel.ProductViewModel
import genstore.id.util.Constants
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onItemClick: (Product) -> Unit
) {

    val context = LocalContext.current

    val products by viewModel.filteredProducts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isEmpty by viewModel.isEmpty.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        // Search bar
        SearchTextField(
            searchQuery = searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
        )

        Box(modifier = Modifier.fillMaxSize()) {

            if (isEmpty && !isLoading) {
                // Empty state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(Constants.NO_PRODUCT, fontSize = 18.sp)
                }
            } else {
                // Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(products) { product ->
                        ProductCard(product, onClick = { onItemClick(product) })
                    }

                    // Load More Button
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        if (!isLoading && searchQuery.isBlank()) {
                            Button(
                                onClick = { viewModel.loadNextPage() },
                                modifier = Modifier.fillMaxWidth().padding(16.dp)
                            ) {
                                Text(Constants.LOAD_MORE)
                            }
                        }
                    }
                }
            }

            // Loading overlay
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}

