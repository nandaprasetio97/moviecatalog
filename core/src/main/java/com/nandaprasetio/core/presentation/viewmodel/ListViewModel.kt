package com.nandaprasetio.core.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import androidx.paging.*
import com.nandaprasetio.core.domain.entity.result.FailedHttpResultThrowable
import com.nandaprasetio.core.domain.usecase.listloader.ListLoaderUseCase
import kotlinx.coroutines.flow.Flow

class ListViewModel<Key: Any, Value: Any>(private val listLoaderUseCase: ListLoaderUseCase<Key, Value>): ViewModel() {
    // List Loading State Live Data
    val listLoadingStateLiveData: LiveData<Int> = MutableLiveData()
    private val listLoadingStateMutableLiveData: MutableLiveData<Int>
        = listLoadingStateLiveData as MutableLiveData

    val listErrorLoadingStateLiveData: LiveData<FailedHttpResultThrowable> = MutableLiveData()
    private val listErrorLoadingStateMutableLiveData: MutableLiveData<FailedHttpResultThrowable>
            = listErrorLoadingStateLiveData as MutableLiveData

    init {
        listLoadingStateMutableLiveData.value = 0
    }

    fun getList(): Flow<PagingData<Value>> {
        return Pager(
            config = listLoaderUseCase.getPagingConfig(),
            pagingSourceFactory = listLoaderUseCase.getPagingSource()
        ).flow.cachedIn(viewModelScope)
    }

    fun setLoadingState(state: Int) {
        listLoadingStateMutableLiveData.value = state
    }

    fun setErrorLoadingState(failedHttpResultThrowable: FailedHttpResultThrowable) {
        listErrorLoadingStateMutableLiveData.value = failedHttpResultThrowable
    }

    @Suppress("UNCHECKED_CAST")
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    suspend fun pagingSourceTesting(
        key: Key,
        loadSize: Int,
        onSuccessLoadResult: (PagingSource.LoadResult.Page<Key, Value>) -> Unit,
        onFailedHttpResult: (PagingSource.LoadResult.Error<Key, Value>) -> Unit
    ) {
        val pagingSource: PagingSource<Key, Value> = listLoaderUseCase.getPagingSource().invoke()
        val loadResult: PagingSource.LoadResult<Key, Value> = pagingSource.load(
            PagingSource.LoadParams.Refresh(key, loadSize, false)
        )
        when (loadResult) {
            is PagingSource.LoadResult.Page -> onSuccessLoadResult.invoke(loadResult)
            is PagingSource.LoadResult.Error -> onFailedHttpResult.invoke(loadResult)
            else -> throw IllegalStateException("Invalid LoadResult.")
        }
    }
}