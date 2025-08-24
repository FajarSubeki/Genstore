package genstore.id.di

import genstore.id.data.repository.ProductRepositoryImpl
import genstore.id.presentation.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Repository
    single { ProductRepositoryImpl(get(), get()) }

    // ViewModel
    viewModel { ProductViewModel(get()) }
}
