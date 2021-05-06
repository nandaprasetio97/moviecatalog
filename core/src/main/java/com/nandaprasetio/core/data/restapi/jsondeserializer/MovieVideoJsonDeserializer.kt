package com.nandaprasetio.core.data.restapi.jsondeserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.nandaprasetio.core.misc.extension.getNextPage
import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.MovieVideoAdapterData
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import java.lang.reflect.Type

class MovieVideoJsonDeserializer: JsonDeserializer<BaseHttpResult<ListLoaderOutput<MovieVideoAdapterData>>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BaseHttpResult<ListLoaderOutput<MovieVideoAdapterData>> {
        return json.asJsonObject.let {
            val videoAdapterDataList: MutableList<MovieVideoAdapterData> = mutableListOf()
            it?.get("results")?.asJsonArray?.forEach { it2 ->
                videoAdapterDataList.add(VideoAdapterData.fromGson(it2.asJsonObject, MovieVideoAdapterData::class.java))
            }
            SuccessHttpResult(
                ListLoaderOutput(
                    resultList = videoAdapterDataList,
                    prevKey = null,
                    nextKey = this.getNextPage(it)
                )
            )
        }
    }
}