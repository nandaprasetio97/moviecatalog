package com.nandaprasetio.core.data.datasource.pagingsource

import androidx.paging.PagingState
import com.nandaprasetio.core.misc.extension.toThrowable
import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.data.listloaderdata.BaseVideoListLoaderData
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.VideoListLoaderDataArgument
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.FailedHttpResult
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoListPagingSource<T: VideoListLoaderDataArgument>(
    private val videoListLoaderData: BaseVideoListLoaderData<T>
): BaseListPagingSource<Int, VideoAdapterData, VideoListLoaderDataArgument>() {
    @Suppress("UNCHECKED_CAST")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoAdapterData> {
        return withContext(Dispatchers.IO) {
            when (
                val baseHttpResult: BaseHttpResult<ListLoaderOutput<VideoAdapterData>> = videoListLoaderData.getList(
                params.key ?: 1
                )) {
                is SuccessHttpResult -> {
                    val listLoaderOutput: ListLoaderOutput<VideoAdapterData> = baseHttpResult.result
                    LoadResult.Page(
                        data = listLoaderOutput.resultList,
                        prevKey = listLoaderOutput.prevKey,
                        nextKey = listLoaderOutput.nextKey
                    )
                }
                is FailedHttpResult -> {
                    LoadResult.Error(baseHttpResult.toThrowable())
                }
                else -> throw IllegalStateException("Output must be Success or Failed.")
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, VideoAdapterData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}