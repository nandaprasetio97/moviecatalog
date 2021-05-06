package com.nandaprasetio.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nandaprasetio.core.domain.usecase.listloader.ListLoaderUseCase

class ListViewModelFactory<Key: Any, Value: Any>(private val listLoaderUseCase: ListLoaderUseCase<Key, Value>): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            return ListViewModel(listLoaderUseCase) as T
        } else {
            throw IllegalStateException("Tipe view model harus berupa ${ListViewModel::class.java.simpleName}.")
        }
    }
}