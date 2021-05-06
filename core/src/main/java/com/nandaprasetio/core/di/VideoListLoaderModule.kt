package com.nandaprasetio.core.di

import com.nandaprasetio.core.data.listloaderdata.BaseVideoListLoaderData
import com.nandaprasetio.core.data.listloaderdata.DefaultVideoListLoaderData
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.MovieVideoListLoaderDataArgument
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.TvShowVideoListLoaderDataArgument
import com.nandaprasetio.core.domain.usecase.listloader.VideoListLoaderUseCase
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val videoListLoaderModule: Module = module {
    factory<BaseVideoListLoaderData<MovieVideoListLoaderDataArgument>>(named<MovieVideoListLoaderDataArgument>()) { params -> DefaultVideoListLoaderData(get(), MovieVideoListLoaderDataArgument(params.get())) }
    factory(named<MovieVideoListLoaderDataArgument>()) { params -> VideoListLoaderUseCase<MovieVideoListLoaderDataArgument>(get(named<MovieVideoListLoaderDataArgument>()) { params }) }
    factory<BaseVideoListLoaderData<TvShowVideoListLoaderDataArgument>>(named<TvShowVideoListLoaderDataArgument>()) { params -> DefaultVideoListLoaderData(get(), TvShowVideoListLoaderDataArgument(params.get())) }
    factory(named<TvShowVideoListLoaderDataArgument>()) { params -> VideoListLoaderUseCase<TvShowVideoListLoaderDataArgument>(get(named<TvShowVideoListLoaderDataArgument>()) { params }) }
}