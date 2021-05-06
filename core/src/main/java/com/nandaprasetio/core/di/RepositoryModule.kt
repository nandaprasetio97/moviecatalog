package com.nandaprasetio.core.di

import com.nandaprasetio.core.data.repository.DefaultVideoRepository
import com.nandaprasetio.core.data.repository.VideoRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<VideoRepository> { DefaultVideoRepository(get(), get()) }
}