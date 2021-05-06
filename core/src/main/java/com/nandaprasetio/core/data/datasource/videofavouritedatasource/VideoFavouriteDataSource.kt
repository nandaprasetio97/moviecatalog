package com.nandaprasetio.core.data.datasource.videofavouritedatasource

import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo

interface VideoFavouriteDataSource {
    suspend fun getMovieVideoFavouriteList(): BaseHttpResult<ListLoaderOutput<VideoAdapterData>>
    suspend fun getMovieVideoIsFavourite(videoId: Int): BaseHttpResult<Boolean>
    suspend fun updateMovieVideoFavourite(movieVideo: MovieVideo): BaseHttpResult<Boolean>
    suspend fun getTvShowVideoFavouriteList(): BaseHttpResult<ListLoaderOutput<VideoAdapterData>>
    suspend fun getTvShowVideoIsFavourite(videoId: Int): BaseHttpResult<Boolean>
    suspend fun updateTvShowVideoFavourite(tvShowVideo: TvShowVideo): BaseHttpResult<Boolean>
}