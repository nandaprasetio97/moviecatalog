package com.nandaprasetio.core.data.datasource.videodatasource

import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.DetailMovieVideo
import com.nandaprasetio.core.domain.entity.video.DetailTvShowVideo

interface VideoDataSource {
    suspend fun getMovieVideoList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>>
    suspend fun getDetailMovieVideoList(videoId: Int): BaseHttpResult<DetailMovieVideo>
    suspend fun getTvShowVideoList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>>
    suspend fun getDetailTvShowVideoList(videoId: Int): BaseHttpResult<DetailTvShowVideo>
}