package com.nandaprasetio.moviecatalog

import androidx.annotation.VisibleForTesting
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import com.nandaprasetio.core.data.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun initVideoFavouriteDataForTesting(videoRepository: VideoRepository) {
    runBlocking(Dispatchers.IO) {
        videoRepository.getMovieVideoList(1).also {
            if (it is SuccessHttpResult) {
                it.result.resultList.forEach { it2 ->
                    videoRepository.updateMovieVideoFavourite(it2.baseVideo as MovieVideo)
                }
            }
        }
        videoRepository.getTvShowVideoList(1).also {
            if (it is SuccessHttpResult) {
                it.result.resultList.forEach { it2 ->
                    videoRepository.updateTvShowVideoFavourite(it2.baseVideo as TvShowVideo)
                }
            }
        }
    }
}