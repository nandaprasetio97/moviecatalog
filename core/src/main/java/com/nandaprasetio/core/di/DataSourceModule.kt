package com.nandaprasetio.core.di

import com.nandaprasetio.core.data.database.AppDatabase
import com.nandaprasetio.core.data.datasource.videodatasource.DefaultVideoDataSource
import com.nandaprasetio.core.data.datasource.videodatasource.VideoDataSource
import com.nandaprasetio.core.data.datasource.videofavouritedatasource.DefaultVideoFavouriteDataSource
import com.nandaprasetio.core.data.datasource.videofavouritedatasource.VideoFavouriteDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

val dataSourceModule: Module = module {
    single<VideoDataSource> { DefaultVideoDataSource() }
    single<VideoFavouriteDataSource> {
        DefaultVideoFavouriteDataSource(get(AppDatabase::class.java).videoFavouriteDao())
    }
}