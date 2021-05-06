package com.nandaprasetio.moviecatalog.presentation.fragment.recyclerviewfragment

import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.MovieVideoListLoaderDataArgument
import com.nandaprasetio.core.domain.usecase.listloader.VideoListLoaderUseCase
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MovieVideoRecyclerViewFragment: BaseVideoRecyclerViewFragment<MovieVideoListLoaderDataArgument>(
    key = "MovieVideo",
) {
    override fun getListLoaderUseCase(): VideoListLoaderUseCase<MovieVideoListLoaderDataArgument>
        = get(named<MovieVideoListLoaderDataArgument>()) { parametersOf(isFavourite()) }
}