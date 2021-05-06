package com.nandaprasetio.core.data.repository

import com.nandaprasetio.core.data.datasource.videodatasource.VideoDataSource
import com.nandaprasetio.core.data.datasource.videofavouritedatasource.VideoFavouriteDataSource
import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.DetailMovieVideo
import com.nandaprasetio.core.domain.entity.video.DetailTvShowVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo

class DefaultVideoRepository(
    private val videoDataSource: VideoDataSource,
    private val videoFavouriteDataSource: VideoFavouriteDataSource
): VideoRepository {
    override suspend fun getMovieVideoList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return videoDataSource.getMovieVideoList(page)
    }

    override suspend fun getMovieVideoFavouriteList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return videoFavouriteDataSource.getMovieVideoFavouriteList()
    }

    override suspend fun getMovieVideoIsFavourite(videoId: Int): BaseHttpResult<Boolean> {
        return videoFavouriteDataSource.getMovieVideoIsFavourite(videoId)
    }

    override suspend fun updateMovieVideoFavourite(movieVideo: MovieVideo): BaseHttpResult<Boolean> {
        return videoFavouriteDataSource.updateMovieVideoFavourite(movieVideo)
    }

    override suspend fun getDetailMovieVideoList(videoId: Int): BaseHttpResult<DetailMovieVideo> {
        return videoDataSource.getDetailMovieVideoList(videoId)
    }

    override suspend fun getTvShowVideoList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return videoDataSource.getTvShowVideoList(page)
    }

    override suspend fun getTvShowVideoFavouriteList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return videoFavouriteDataSource.getTvShowVideoFavouriteList()
    }

    override suspend fun getTvShowVideoIsFavourite(videoId: Int): BaseHttpResult<Boolean> {
        return videoFavouriteDataSource.getTvShowVideoIsFavourite(videoId)
    }

    override suspend fun getDetailTvShowVideoList(videoId: Int): BaseHttpResult<DetailTvShowVideo> {
        return videoDataSource.getDetailTvShowVideoList(videoId)
    }

    override suspend fun updateTvShowVideoFavourite(tvShowVideo: TvShowVideo): BaseHttpResult<Boolean> {
        return videoFavouriteDataSource.updateTvShowVideoFavourite(tvShowVideo)
    }
}