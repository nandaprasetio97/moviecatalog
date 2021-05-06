@file:Suppress("unused")

package com.nandaprasetio.moviecatalog

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.nandaprasetio.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieCatalogApplication: SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            this.androidLogger(Level.NONE)
            this.androidContext(this@MovieCatalogApplication)
            this.modules(
                databaseModule,
                networkModule,
                dataSourceModule,
                repositoryModule,
                detailVideoModule,
                videoListLoaderModule,
                viewModelModule
            )
        }
    }
}