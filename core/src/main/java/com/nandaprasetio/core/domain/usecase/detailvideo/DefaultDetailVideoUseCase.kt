package com.nandaprasetio.core.domain.usecase.detailvideo

import com.nandaprasetio.core.misc.extension.map
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.FailedHttpResult
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import com.nandaprasetio.core.domain.entity.video.*
import com.nandaprasetio.core.data.repository.VideoRepository

class DefaultDetailVideoUseCase(private val videoRepository: VideoRepository): DetailVideoUseCase {
    override suspend fun getDetailMovieVideoList(videoId: Int): BaseHttpResult<DetailVideoWrapper<DetailMovieVideo>> {
        return getDetailVideoList(
            { videoRepository.getDetailMovieVideoList(videoId) },
            { videoRepository.getMovieVideoIsFavourite(videoId) }
        )
    }

    override suspend fun getDetailTvShowVideoList(videoId: Int): BaseHttpResult<DetailVideoWrapper<DetailTvShowVideo>> {
        return getDetailVideoList(
            { videoRepository.getDetailTvShowVideoList(videoId) },
            { videoRepository.getTvShowVideoIsFavourite(videoId) }
        )
    }

    override suspend fun updateMovieVideoFavourite(movieVideo: MovieVideo): BaseHttpResult<Boolean> {
        return videoRepository.updateMovieVideoFavourite(movieVideo)
    }

    override suspend fun updateTvShowVideoFavourite(tvShowVideo: TvShowVideo): BaseHttpResult<Boolean> {
        return videoRepository.updateTvShowVideoFavourite(tvShowVideo)
    }

    private suspend fun<T: BaseVideo> getDetailVideoList(
        onGetDetailVideo: suspend () -> BaseHttpResult<T>,
        onGetVideoIsFavourite: suspend () -> BaseHttpResult<Boolean>
    ): BaseHttpResult<DetailVideoWrapper<T>> {
        return onGetDetailVideo().let {
            when (it) {
                is SuccessHttpResult -> {
                    onGetVideoIsFavourite().let { it2 ->
                        when (it2) {
                            is SuccessHttpResult -> {
                                SuccessHttpResult(DetailVideoWrapper(it.result, it2.result))
                            }
                            is FailedHttpResult -> it2.map()
                            else -> throw IllegalStateException("Invalid HttpResult")
                        }
                    }
                }
                is FailedHttpResult -> it.map()
                else -> throw IllegalStateException("Invalid HttpResult")
            }
        }
    }
}