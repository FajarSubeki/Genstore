package genstore.id.di

import androidx.room.Room
import genstore.id.data.local.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().productDao() }
}
