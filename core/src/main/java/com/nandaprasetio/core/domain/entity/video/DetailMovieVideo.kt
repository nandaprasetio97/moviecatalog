package com.nandaprasetio.core.domain.entity.video

import com.google.gson.JsonObject
import com.nandaprasetio.core.misc.helper.DateHelper
import java.util.*

class DetailMovieVideo(
    id: Int,
    titleOrName: String?,
    originalTitleOrName: String?,
    originalLanguange: String?,
    genresId: List<Int>,
    overview: String?,
    posterPath: String?,
    backdropPath: String?,
    popularity: Float,
    voteCount: Int,
    voteAverage: Float,
    adult: Boolean,
    releaseDate: Date?,
    video: Boolean
): MovieVideo(
    id, titleOrName, originalTitleOrName, originalLanguange, genresId, overview, posterPath,
    backdropPath, popularity, voteCount, voteAverage, adult, releaseDate, video
) {
    companion object {
        fun fromGson(jsonObject: JsonObject): DetailMovieVideo {
            return DetailMovieVideo(
                id = jsonObject.get("id").asInt,
                titleOrName = jsonObject.get("title").asString,
                originalTitleOrName = jsonObject.get("original_title").asString,
                genresId = jsonObject.get("genres").asJsonArray.map { it.asJsonObject.get("id").asInt },
                originalLanguange = jsonObject.get("original_language").asString,
                overview = jsonObject.get("overview").asString,
                posterPath = jsonObject.get("poster_path").asString,
                backdropPath = jsonObject.get("backdrop_path").asString,
                popularity = jsonObject.get("popularity").asFloat,
                voteCount = jsonObject.get("vote_count").asInt,
                voteAverage = jsonObject.get("vote_average").asFloat,
                adult = jsonObject.get("adult").asBoolean,
                releaseDate = DateHelper.parseDate(jsonObject.get("release_date").asString),
                video = jsonObject.get("video").asBoolean
            )
        }
    }
}