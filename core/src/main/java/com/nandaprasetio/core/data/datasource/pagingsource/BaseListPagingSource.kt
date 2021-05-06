package com.nandaprasetio.core.data.datasource.pagingsource

import androidx.paging.PagingSource
import com.nandaprasetio.core.data.listloaderdataargument.ListLoaderDataArgument
import com.nandaprasetio.core.domain.entity.adapterdata.BaseAdapterData

abstract class BaseListPagingSource<Key: Any, Value: BaseAdapterData, T: ListLoaderDataArgument>
    : PagingSource<Key, Value>()