package com.nandaprasetio.core.data.listloaderdata

import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.MovieVideoListLoaderDataArgument
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.TvShowVideoListLoaderDataArgument
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.VideoListLoaderDataArgument
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.data.repository.VideoRepository
import com.nandaprasetio.core.misc.extension.checkDataIsEmpty

class DefaultVideoListLoaderData<T: VideoListLoaderDataArgument>(
    private val videoRepository: VideoRepository,
    private val listLoaderDataArgument: T
): BaseVideoListLoaderData<T>(listLoaderDataArgument) {
    override suspend fun getList(page: Int): BaseHttpResult<ListLoaderOutput<VideoAdapterData>> {
        return when(listLoaderDataArgument) {
            is MovieVideoListLoaderDataArgument -> if (!listLoaderDataArgument.favourite) {
                videoRepository.getMovieVideoList(page)
            } else {
                videoRepository.getMovieVideoFavouriteList(page)
            }
            is TvShowVideoListLoaderDataArgument -> if (!listLoaderDataArgument.favourite) {
                videoRepository.getTvShowVideoList(page)
            } else {
                videoRepository.getTvShowVideoFavouriteList(page)
            }
            else -> throw IllegalStateException("Invalid argument.")
        }.checkDataIsEmpty()
    }
}