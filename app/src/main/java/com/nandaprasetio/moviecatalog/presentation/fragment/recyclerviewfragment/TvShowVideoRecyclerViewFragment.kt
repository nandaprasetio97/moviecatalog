package com.nandaprasetio.moviecatalog.presentation.fragment.recyclerviewfragment

import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.TvShowVideoListLoaderDataArgument
import com.nandaprasetio.core.domain.usecase.listloader.VideoListLoaderUseCase
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class TvShowVideoRecyclerViewFragment: BaseVideoRecyclerViewFragment<TvShowVideoListLoaderDataArgument>(
    key = "TvShowVideo",
) {
    override fun getListLoaderUseCase(): VideoListLoaderUseCase<TvShowVideoListLoaderDataArgument>
        = get(named<TvShowVideoListLoaderDataArgument>()) { parametersOf(isFavourite()) }
}