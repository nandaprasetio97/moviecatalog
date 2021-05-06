package com.nandaprasetio.core.domain.usecase.listloader

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.nandaprasetio.core.data.datasource.pagingsource.VideoListPagingSource
import com.nandaprasetio.core.data.listloaderdata.BaseVideoListLoaderData
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.VideoListLoaderDataArgument
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData

class VideoListLoaderUseCase<T: VideoListLoaderDataArgument>(
    private val videoListLoaderData: BaseVideoListLoaderData<T>
): ListLoaderUseCase<Int, VideoAdapterData> {
    override fun getPagingSource(): () -> PagingSource<Int, VideoAdapterData> {
        return { VideoListPagingSource(videoListLoaderData) }
    }

    override fun getPagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 20,
            prefetchDistance = 2
        )
    }
}