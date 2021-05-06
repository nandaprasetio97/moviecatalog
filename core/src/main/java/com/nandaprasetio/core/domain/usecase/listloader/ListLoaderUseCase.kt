package com.nandaprasetio.core.domain.usecase.listloader

import androidx.paging.PagingConfig
import androidx.paging.PagingSource

interface ListLoaderUseCase<Key: Any, Value: Any> {
    fun getPagingSource(): () -> PagingSource<Key, Value>
    fun getPagingConfig(): PagingConfig
}