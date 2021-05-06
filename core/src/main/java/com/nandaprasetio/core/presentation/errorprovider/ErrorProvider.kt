package com.nandaprasetio.core.presentation.errorprovider

import android.content.Context
import com.nandaprasetio.core.domain.entity.result.FailedHttpResultThrowable

interface ErrorProvider {
    fun getMessage(context: Context, failedHttpResultThrowable: FailedHttpResultThrowable): ErrorProviderResult?
}