package com.nandaprasetio.core.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.nandaprasetio.core.data.database.AppDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

private val applyDatabaseEncryption: RoomDatabase.Builder<AppDatabase>.() -> Unit = {
    this.openHelperFactory(SupportFactory(SQLiteDatabase.getBytes("dicoding".toCharArray())))
}

val databaseModule: Module = module {
    single {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, "movie_catalog"
        ).apply(applyDatabaseEncryption).build()
    }
}

val testingDatabaseModule = module {
    single {
        Room.inMemoryDatabaseBuilder(
            androidContext(),
            AppDatabase::class.java
        ).build()
    }
}