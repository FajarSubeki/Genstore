package genstore.id.di

import genstore.id.data.remote.ProductApi
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) { json(
                kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                }
            ) }
            install(Logging)
        }
    }
    single { ProductApi(get()) }
}
