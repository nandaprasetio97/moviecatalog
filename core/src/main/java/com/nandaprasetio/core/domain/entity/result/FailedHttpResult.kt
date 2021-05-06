package com.nandaprasetio.core.domain.entity.result

class FailedHttpResult<T>(
    val statusCode: Int,
    val message: String?,
    val exception: Exception?
): BaseHttpResult<T>()