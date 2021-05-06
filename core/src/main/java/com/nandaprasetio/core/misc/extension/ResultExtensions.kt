package com.nandaprasetio.core.misc.extension

import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.FailedHttpResult
import com.nandaprasetio.core.domain.entity.result.FailedHttpResultThrowable
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult

fun<T> FailedHttpResult<T>.toThrowable(): FailedHttpResultThrowable {
    return FailedHttpResultThrowable(this.statusCode, this.message, this.exception)
}

fun<T, O> FailedHttpResult<T>.map(): FailedHttpResult<O> {
    return FailedHttpResult(
        statusCode = this.statusCode,
        message = this.message,
        exception = this.exception
    )
}

fun<T> BaseHttpResult<ListLoaderOutput<T>>.checkDataIsEmpty(): BaseHttpResult<ListLoaderOutput<T>> {
    return this.let {
        if (it is SuccessHttpResult) {
            if (it.result.resultList.isNullOrEmpty()) {
                return@let FailedHttpResult(-1, null, null)
            }
        }

        it
    }
}