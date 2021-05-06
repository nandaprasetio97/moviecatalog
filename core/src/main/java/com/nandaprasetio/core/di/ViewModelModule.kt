package com.nandaprasetio.core.di

import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import com.nandaprasetio.core.presentation.viewmodel.DetailVideoViewModel
import com.nandaprasetio.core.presentation.viewmodel.ListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { params -> ListViewModel<Int, VideoAdapterData>(get { parametersOf(params) }) }
    viewModel(named<MovieVideo>()) { params -> DetailVideoViewModel(MovieVideo::class.java, params.get(), get()) }
    viewModel(named<TvShowVideo>()) { params -> DetailVideoViewModel(TvShowVideo::class.java, params.get(), get()) }
}