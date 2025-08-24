package genstore.id

import android.app.Application
import genstore.id.di.appModule
import genstore.id.di.databaseModule
import genstore.id.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    appModule
                )
            )
        }
    }
}
