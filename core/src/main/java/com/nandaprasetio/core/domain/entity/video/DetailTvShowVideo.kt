package com.nandaprasetio.core.domain.entity.video

import com.google.gson.JsonObject
import com.nandaprasetio.core.misc.helper.DateHelper
import java.util.*

class DetailTvShowVideo(
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
    firstAirDate: Date?,
    originalCountry: List<String?>
): TvShowVideo(
    id, titleOrName, originalTitleOrName, originalLanguange, genresId, overview, posterPath,
    backdropPath, popularity, voteCount, voteAverage, firstAirDate, originalCountry
) {
    companion object {
        fun fromGson(jsonObject: JsonObject): DetailTvShowVideo {
            return DetailTvShowVideo(
                id = jsonObject.get("id").asInt,
                titleOrName = jsonObject.get("name").asString,
                originalTitleOrName = jsonObject.get("original_name").asString,
                genresId = jsonObject.get("genres").asJsonArray.map { it.asJsonObject.get("id").asInt },
                originalLanguange = jsonObject.get("original_language").asString,
                overview = jsonObject.get("overview").asString,
                posterPath = jsonObject.get("poster_path").asString,
                backdropPath = jsonObject.get("backdrop_path").asString,
                popularity = jsonObject.get("popularity").asFloat,
                voteCount = jsonObject.get("vote_count").asInt,
                voteAverage = jsonObject.get("vote_average").asFloat,
                firstAirDate = DateHelper.parseDate(jsonObject.get("first_air_date").asString),
                originalCountry = jsonObject.get("origin_country").asJsonArray.map { it.asString }
            )
        }
    }
}