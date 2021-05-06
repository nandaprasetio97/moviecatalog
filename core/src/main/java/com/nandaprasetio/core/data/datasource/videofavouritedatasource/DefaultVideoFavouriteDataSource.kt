package com.nandaprasetio.core.data.datasource.videofavouritedatasource

import com.nandaprasetio.core.data.database.DatabaseContract
import com.nandaprasetio.core.data.database.dao.VideoFavouriteDao
import com.nandaprasetio.core.misc.extension.toBaseVideo
import com.nandaprasetio.core.misc.extension.toVideoFavourite
import com.nandaprasetio.core.misc.extension.toVideoType
import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.VideoFavourite
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import com.nandaprasetio.core.data.restapi.getResponse

class DefaultVideoFavouriteDataSource(private val videoFavouriteDao: VideoFavouriteDao): VideoFavouriteDataSource {
    override suspend fun getMovieVideoFavouriteList(): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return getResponse {
            getVideoFavouriteList {
                videoFavouriteDao.getBasedVideoType(DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE_MOVIE)
            }
        }
    }

    override suspend fun getMovieVideoIsFavourite(videoId: Int): BaseHttpResult<Boolean> {
        return getResponse {
            getVideoIsFavourite {
                videoFavouriteDao.getCountBasedVideoIdAndVideoType(
                    videoId, DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE_MOVIE
                )
            }
        }
    }

    override suspend fun updateMovieVideoFavourite(movieVideo: MovieVideo): BaseHttpResult<Boolean> {
        return updateVideoFavourite(
            { getMovieVideoIsFavourite(movieVideo.id) },
            { videoFavouriteDao.insert(movieVideo.toVideoFavourite()) },
            { deleteVideoFavourite(movieVideo) }
        )
    }

    override suspend fun getTvShowVideoFavouriteList(): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return getResponse {
            getVideoFavouriteList {
                videoFavouriteDao.getBasedVideoType(DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE_TV_SHOW)
            }
        }
    }

    override suspend fun getTvShowVideoIsFavourite(videoId: Int): BaseHttpResult<Boolean> {
        return getResponse {
            getVideoIsFavourite {
                videoFavouriteDao.getCountBasedVideoIdAndVideoType(
                    videoId, DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE_TV_SHOW
                )
            }
        }
    }

    override suspend fun updateTvShowVideoFavourite(tvShowVideo: TvShowVideo): BaseHttpResult<Boolean> {
        return updateVideoFavourite(
            { getTvShowVideoIsFavourite(tvShowVideo.id) },
            { videoFavouriteDao.insert(tvShowVideo.toVideoFavourite()) },
            { deleteVideoFavourite(tvShowVideo) }
        )
    }

    private suspend fun getVideoFavouriteList(onGetVideoFavourite: () -> List<VideoFavourite>): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return getResponse {
            SuccessHttpResult(
                ListLoaderOutput(
                    resultList = onGetVideoFavourite().map {
                        VideoAdapterData(it.toBaseVideo)
                    },
                    prevKey = null,
                    nextKey = null
                )
            )
        }
    }

    private fun getVideoIsFavourite(onGetVideoFavouriteCount: () -> Int): BaseHttpResult<Boolean> {
        return SuccessHttpResult(onGetVideoFavouriteCount() > 0)
    }

    private suspend fun updateVideoFavourite(
        onGetVideoIsFavourite: suspend () -> BaseHttpResult<Boolean>,
        onInsertVideoFavourite: () -> Unit,
        onDeleteVideoFavourite: () -> Unit
    ): BaseHttpResult<Boolean> {
        return getResponse {
            onGetVideoIsFavourite().let {
                when (it) {
                    is SuccessHttpResult -> {
                        SuccessHttpResult(
                            if (!it.result) {
                                onInsertVideoFavourite()
                                true
                            } else {
                                onDeleteVideoFavourite()
                                false
                            }
                        )
                    }
                    else -> it
                }
            }
        }
    }

    private fun deleteVideoFavourite(baseVideo: BaseVideo) {
        videoFavouriteDao.deleteBasedIdAndVideoType(
            videoId = baseVideo.id.toLong(),
            videoType = baseVideo.toVideoType
        )
    }
}