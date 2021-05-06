package com.nandaprasetio.core.data.datasource.videodatasource

import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.DetailMovieVideo
import com.nandaprasetio.core.domain.entity.video.DetailTvShowVideo
import com.nandaprasetio.core.data.restapi.RetrofitHttpClient
import com.nandaprasetio.core.data.restapi.getHttpResponse
import com.nandaprasetio.core.data.restapi.restapiinterface.VideoRestApiInterface

class DefaultVideoDataSource: VideoDataSource {
    private val videoRestApiInterface: VideoRestApiInterface = RetrofitHttpClient.of().create(VideoRestApiInterface::class.java)

    override suspend fun getMovieVideoList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return getHttpResponse {
            videoRestApiInterface.getPopularMovieVideo(page)
        }
    }

    override suspend fun getDetailMovieVideoList(videoId: Int): BaseHttpResult<DetailMovieVideo> {
        return getHttpResponse {
            videoRestApiInterface.getDetailMovieVideo(videoId)
        }
    }

    override suspend fun getTvShowVideoList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return getHttpResponse {
            videoRestApiInterface.getPopularTvVideo(page)
        }
    }

    override suspend fun getDetailTvShowVideoList(tvId: Int): BaseHttpResult<DetailTvShowVideo> {
        return getHttpResponse {
            videoRestApiInterface.getDetailTvVideo(tvId)
        }
    }
}