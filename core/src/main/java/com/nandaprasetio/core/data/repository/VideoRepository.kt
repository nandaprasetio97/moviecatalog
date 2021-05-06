package com.nandaprasetio.core.data.repository

import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.DetailMovieVideo
import com.nandaprasetio.core.domain.entity.video.DetailTvShowVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo

interface VideoRepository {
    suspend fun getMovieVideoList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>>
    suspend fun getMovieVideoFavouriteList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>>
    suspend fun getMovieVideoIsFavourite(videoId: Int): BaseHttpResult<Boolean>
    suspend fun getDetailMovieVideoList(videoId: Int): BaseHttpResult<DetailMovieVideo>
    suspend fun updateMovieVideoFavourite(movieVideo: MovieVideo): BaseHttpResult<Boolean>
    suspend fun getTvShowVideoList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>>
    suspend fun getTvShowVideoFavouriteList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>>
    suspend fun getTvShowVideoIsFavourite(videoId: Int): BaseHttpResult<Boolean>
    suspend fun getDetailTvShowVideoList(videoId: Int): BaseHttpResult<DetailTvShowVideo>
    suspend fun updateTvShowVideoFavourite(tvShowVideo: TvShowVideo): BaseHttpResult<Boolean>
}