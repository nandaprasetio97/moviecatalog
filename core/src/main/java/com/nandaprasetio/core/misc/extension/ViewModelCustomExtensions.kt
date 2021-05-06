package com.nandaprasetio.core.misc.extension

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.FailedHttpResult
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult

@Suppress("unused")
fun<T> ViewModel.changeStateBaseHttpResult(
    mutableLiveData: MutableLiveData<Int>,
    baseHttpResult: BaseHttpResult<T>
) {
    if (baseHttpResult is SuccessHttpResult) {
        mutableLiveData.postValue(0)
    } else if (baseHttpResult is FailedHttpResult) {
        mutableLiveData.postValue(-2)
    }
}