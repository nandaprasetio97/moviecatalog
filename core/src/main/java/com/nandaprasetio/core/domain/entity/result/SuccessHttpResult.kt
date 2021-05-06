package com.nandaprasetio.core.domain.entity.result

class SuccessHttpResult<T>(
    val result: T
): BaseHttpResult<T>()