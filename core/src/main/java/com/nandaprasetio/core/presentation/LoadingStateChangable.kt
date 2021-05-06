package com.nandaprasetio.core.presentation

import com.nandaprasetio.core.presentation.errorprovider.ErrorProviderResult

interface LoadingStateChangable {
    fun onLoadingStateChange(state: Int)
    fun onLoadingFailed(errorProviderResult: ErrorProviderResult?)
}