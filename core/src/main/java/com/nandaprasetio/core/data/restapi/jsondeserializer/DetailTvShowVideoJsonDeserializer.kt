package com.nandaprasetio.core.data.restapi.jsondeserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import com.nandaprasetio.core.domain.entity.video.DetailTvShowVideo
import java.lang.reflect.Type

class DetailTvShowVideoJsonDeserializer: JsonDeserializer<BaseHttpResult<DetailTvShowVideo>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BaseHttpResult<DetailTvShowVideo> {
        return SuccessHttpResult(DetailTvShowVideo.fromGson(json.asJsonObject))
    }
}