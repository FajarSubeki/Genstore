package genstore.id.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import genstore.id.domain.model.Product
import genstore.id.presentation.ui.list.ProductListScreen
import genstore.id.presentation.ui.detail.ProductDetailScreen
import org.koin.androidx.compose.koinViewModel
import com.google.gson.Gson
import androidx.navigation.NavType
import androidx.navigation.navArgument
import genstore.id.util.Constants

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.ProductList.route) {

        composable(Screen.ProductList.route) {
            ProductListScreen(
                viewModel = koinViewModel(),
                onItemClick = { product ->
                    navController.navigateToDetail(product)
                }
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument(Constants.ARG_PRODUCT_JSON) { type = NavType.StringType })
        ) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString(Constants.ARG_PRODUCT_JSON) ?: ""
            val product = Gson().fromJson(productJson, Product::class.java)

            ProductDetailScreen(
                product = product,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
