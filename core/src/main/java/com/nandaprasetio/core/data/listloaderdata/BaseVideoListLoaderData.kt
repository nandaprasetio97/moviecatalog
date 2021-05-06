package com.nandaprasetio.core.data.listloaderdata

import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.VideoListLoaderDataArgument
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData

abstract class BaseVideoListLoaderData<T: VideoListLoaderDataArgument>(
    listLoaderDataArgument: T
): BaseListLoaderData<T, VideoAdapterData>(listLoaderDataArgument)