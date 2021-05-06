package com.nandaprasetio.core.data.listloaderdata

import com.nandaprasetio.core.data.listloaderdataargument.ListLoaderDataArgument
import com.nandaprasetio.core.domain.entity.adapterdata.BaseAdapterData

abstract class BaseListLoaderData<T: ListLoaderDataArgument, O: BaseAdapterData>(
    private val listLoaderDataArgument: T
): ListLoaderData<O>