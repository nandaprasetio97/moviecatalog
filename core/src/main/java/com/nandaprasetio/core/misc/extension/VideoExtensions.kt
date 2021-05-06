package com.nandaprasetio.core.misc.extension

import com.google.gson.JsonObject
import com.nandaprasetio.core.data.database.DatabaseContract
import com.nandaprasetio.core.misc.helper.DateHelper
import com.nandaprasetio.core.domain.entity.VideoFavourite
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo

val BaseVideo.toVideoType: String
    get() {
        return when (this) {
            is MovieVideo -> DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE_MOVIE
            is TvShowVideo -> DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE_TV_SHOW
            else -> throw IllegalStateException("Invalid video")
        }
    }

fun BaseVideo.toVideoFavourite(): VideoFavourite {
    return VideoFavourite(
        id = null,
        videoId = this.id,
        videoType = this.toVideoType,
        titleOrName = this.titleOrName,
        originalTitleOrName = this.originalTitleOrName,
        overview = this.overview,
        posterPath = this.posterPath,
        backdropPath = this.backdropPath
    )
}

val VideoFavourite.toBaseVideo: BaseVideo
    get() {
        return when (this.videoType) {
            DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE_MOVIE -> MovieVideo(
                id = this.videoId,
                titleOrName = this.titleOrName,
                originalTitleOrName = this.originalTitleOrName,
                genresId = listOf(),
                originalLanguange = null,
                overview = this.overview,
                posterPath = this.posterPath,
                backdropPath = this.backdropPath,
                popularity = 0.0f,
                voteCount = 0,
                voteAverage = 0.0f,
                adult = false,
                releaseDate = null,
                video = false
            )
            DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE_TV_SHOW -> TvShowVideo(
                id = this.videoId,
                titleOrName = this.titleOrName,
                originalTitleOrName = this.originalTitleOrName,
                genresId = listOf(),
                originalLanguange = null,
                overview = this.overview,
                posterPath = this.posterPath,
                backdropPath = this.backdropPath,
                popularity = 0.0f,
                voteCount = 0,
                voteAverage = 0.0f,
                firstAirDate = null,
                originalCountry = listOf()
            )
            else -> {
                throw IllegalStateException("Video favourite type is not related with video type.")
            }
        }
    }

val JsonObject.toMovieVideo: MovieVideo
    get() {
        return MovieVideo(
            id = this.get("id").asInt,
            titleOrName = this.get("title").asStringOrNull,
            originalTitleOrName = this.get("original_title").asStringOrNull,
            genresId = this.get("genre_ids").asJsonArray.map { it.asInt },
            originalLanguange = this.get("original_language").asStringOrNull,
            overview = this.get("overview").asStringOrNull,
            posterPath = this.get("poster_path").asStringOrNull,
            backdropPath = this.get("backdrop_path").asStringOrNull,
            popularity = this.get("popularity").asFloat,
            voteCount = this.get("vote_count").asInt,
            voteAverage = this.get("vote_average").asFloat,
            adult = this.get("adult").asBoolean,
            releaseDate = DateHelper.parseDate(this.get("release_date")?.asStringOrNull),
            video = this.get("video").asBoolean
        )
    }

val JsonObject.toTvShowVideo: TvShowVideo
    get() {
        return TvShowVideo(
            id = this.get("id").asInt,
            titleOrName = this.get("name").asStringOrNull,
            originalTitleOrName = this.get("original_name").asStringOrNull,
            genresId = this.get("genre_ids").asJsonArray.map { it.asInt },
            originalLanguange = this.get("original_language").asStringOrNull,
            overview = this.get("overview").asStringOrNull,
            posterPath = this.get("poster_path").asStringOrNull,
            backdropPath = this.get("backdrop_path").asStringOrNull,
            popularity = this.get("popularity").asFloat,
            voteCount = this.get("vote_count").asInt,
            voteAverage = this.get("vote_average").asFloat,
            firstAirDate = DateHelper.parseDate(this.get("first_air_date")?.asStringOrNull),
            originalCountry = this.get("origin_country").asJsonArray.map { it.asStringOrNull }
        )
    }