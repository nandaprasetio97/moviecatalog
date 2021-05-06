package com.nandaprasetio.core.domain.entity.result

class FailedHttpResultThrowable(
    val statusCode: Int,
    message: String?,
    exception: Exception?
): Throwable(message, exception)