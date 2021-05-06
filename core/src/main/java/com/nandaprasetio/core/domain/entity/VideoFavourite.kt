package com.nandaprasetio.core.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nandaprasetio.core.data.database.DatabaseContract

@Entity(tableName = DatabaseContract.VideoFavouriteColumns.TABLE_NAME)
class VideoFavourite(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = DatabaseContract.VideoFavouriteColumns.VIDEO_ID) val videoId: Int,
    @ColumnInfo(name = DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE) val videoType: String?,
    @ColumnInfo(name = DatabaseContract.VideoFavouriteColumns.TITLE_OR_NAME) val titleOrName: String?,
    @ColumnInfo(name = DatabaseContract.VideoFavouriteColumns.ORIGINAL_TITLE_OR_NAME) val originalTitleOrName: String?,
    @ColumnInfo(name = DatabaseContract.VideoFavouriteColumns.OVERVIEW) val overview: String?,
    @ColumnInfo(name = DatabaseContract.VideoFavouriteColumns.POSTER_PATH) val posterPath: String?,
    @ColumnInfo(name = DatabaseContract.VideoFavouriteColumns.BACKDROP_PATH) val backdropPath: String?
)