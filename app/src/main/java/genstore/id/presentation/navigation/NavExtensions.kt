package genstore.id.presentation.navigation


import android.net.Uri
import androidx.navigation.NavController
import com.google.gson.Gson
import genstore.id.domain.model.Product

fun NavController.navigateToDetail(product: Product) {
    val json = Uri.encode(Gson().toJson(product))
    this.navigate("product_detail/$json")
}
