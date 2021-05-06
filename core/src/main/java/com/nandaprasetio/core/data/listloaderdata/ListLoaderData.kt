package com.nandaprasetio.core.data.listloaderdata

import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.BaseAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult

interface ListLoaderData<O: BaseAdapterData> {
    suspend fun getList(page: Int): BaseHttpResult<ListLoaderOutput<O>>
}