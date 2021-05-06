package com.nandaprasetio.core.domain.usecase.detailvideo

import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.*

interface DetailVideoUseCase {
    suspend fun getDetailMovieVideoList(videoId: Int): BaseHttpResult<DetailVideoWrapper<DetailMovieVideo>>
    suspend fun getDetailTvShowVideoList(videoId: Int): BaseHttpResult<DetailVideoWrapper<DetailTvShowVideo>>
    suspend fun updateMovieVideoFavourite(movieVideo: MovieVideo): BaseHttpResult<Boolean>
    suspend fun updateTvShowVideoFavourite(tvShowVideo: TvShowVideo): BaseHttpResult<Boolean>
}