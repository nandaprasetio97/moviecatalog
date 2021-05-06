package com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata

import com.google.gson.JsonObject
import com.nandaprasetio.core.misc.extension.toMovieVideo
import com.nandaprasetio.core.misc.extension.toTvShowVideo
import com.nandaprasetio.core.domain.entity.adapterdata.BaseAdapterData
import com.nandaprasetio.core.domain.entity.video.*

open class VideoAdapterData(
    val baseVideo: BaseVideo
): BaseAdapterData() {
    companion object {
        @Suppress("UNCHECKED_CAST")
        fun<T: VideoAdapterData> fromGson(jsonObject: JsonObject, clazz: Class<T>): T {
            return when {
                clazz.isAssignableFrom(MovieVideoAdapterData::class.java) -> {
                    MovieVideoAdapterData(jsonObject.toMovieVideo) as T
                }
                clazz.isAssignableFrom(TvShowVideoAdapterData::class.java) -> {
                    TvShowVideoAdapterData(jsonObject.toTvShowVideo) as T
                }
                else -> throw IllegalStateException("Class harus berupa MovieVideo dan TvShowVideo")
            }
        }
    }
}