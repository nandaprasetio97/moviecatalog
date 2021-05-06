package com.nandaprasetio.core.presentation.errorprovider

import android.content.Context
import com.nandaprasetio.core.R
import com.nandaprasetio.core.domain.entity.result.FailedHttpResultThrowable

class BaseErrorProvider: ErrorProvider {
    override fun getMessage(context: Context, failedHttpResultThrowable: FailedHttpResultThrowable): ErrorProviderResult {
        return if (failedHttpResultThrowable.statusCode == -1) {
            ErrorProviderResult(context.getString(R.string.no_data))
        } else {
            ErrorProviderResult("${context.getString(R.string.something_failed)} (${failedHttpResultThrowable.message})")
        }
    }
}