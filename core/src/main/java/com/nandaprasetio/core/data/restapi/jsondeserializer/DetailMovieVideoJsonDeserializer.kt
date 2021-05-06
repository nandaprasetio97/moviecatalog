package com.nandaprasetio.core.data.restapi.jsondeserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import com.nandaprasetio.core.domain.entity.video.DetailMovieVideo
import java.lang.reflect.Type

class DetailMovieVideoJsonDeserializer: JsonDeserializer<BaseHttpResult<DetailMovieVideo>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BaseHttpResult<DetailMovieVideo> {
        return SuccessHttpResult(DetailMovieVideo.fromGson(json.asJsonObject))
    }
}