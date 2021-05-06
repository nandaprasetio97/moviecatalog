package com.nandaprasetio.core.data.restapi

import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.FailedHttpResult
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun<T> getHttpResponse(onGetResponse: suspend () -> Response<BaseHttpResult<T>>): BaseHttpResult<T> {
    return getResponse { onGetResponse().body() }
}

suspend fun<T> getResponse(onGetResponse: suspend () -> BaseHttpResult<T>?): BaseHttpResult<T> {
    return try {
        onGetResponse() ?: throw NullPointerException("Response tidak boleh null")
    } catch (e: HttpException) {
        FailedHttpResult(e.code(), e.message(), e)
    } catch (e: IOException) {
        FailedHttpResult(-2, e.message, e)
    } catch (e: NullPointerException) {
        FailedHttpResult(-3, e.message, e)
    } catch (e: Exception) {
        FailedHttpResult(0, e.message, e)
    }
}