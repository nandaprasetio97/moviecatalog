@file:Suppress("unused", "unused", "unused", "unused")

package com.nandaprasetio.core.data.database

class DatabaseContract {
    class VideoFavouriteColumns {
        companion object {
            const val TABLE_NAME = "video_favourite"

            const val ID = "id"
            const val VIDEO_ID = "videoId"
            const val TITLE_OR_NAME = "titleOrName"
            const val VIDEO_TYPE = "videoType"
            const val ORIGINAL_TITLE_OR_NAME = "originalTitleOrName"
            const val OVERVIEW = "overview"
            const val POSTER_PATH = "posterPath"
            const val BACKDROP_PATH = "backdropPath"

            const val VIDEO_TYPE_MOVIE = "movie"
            const val VIDEO_TYPE_TV_SHOW = "tv_show"
        }
    }
}