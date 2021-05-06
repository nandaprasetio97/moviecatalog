package com.nandaprasetio.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nandaprasetio.core.data.database.DatabaseContract
import com.nandaprasetio.core.domain.entity.VideoFavourite

@Dao
interface VideoFavouriteDao {
    @Query("SELECT * FROM ${DatabaseContract.VideoFavouriteColumns.TABLE_NAME}")
    fun getAll(): List<VideoFavourite>

    @Query("SELECT * FROM ${DatabaseContract.VideoFavouriteColumns.TABLE_NAME} WHERE " +
            "videoType = :${DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE}")
    fun getBasedVideoType(videoType: String?): List<VideoFavourite>

    @Query("SELECT * FROM ${DatabaseContract.VideoFavouriteColumns.TABLE_NAME} WHERE " +
            "videoId = :${DatabaseContract.VideoFavouriteColumns.VIDEO_ID} " +
            "AND videoType = :${DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE}")
    fun getBasedVideoIdAndVideoType(videoId: Int, videoType: String?): List<VideoFavourite>

    @Query("SELECT COUNT(*) AS count FROM ${DatabaseContract.VideoFavouriteColumns.TABLE_NAME} WHERE " +
            "videoId = :${DatabaseContract.VideoFavouriteColumns.VIDEO_ID} " +
            "AND videoType = :${DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE}")
    fun getCountBasedVideoIdAndVideoType(videoId: Int, videoType: String?): Int

    @Insert
    fun insert(videoFavourite: VideoFavourite): Long

    @Update
    fun update(videoFavourite: VideoFavourite): Int

    @Query("DELETE FROM ${DatabaseContract.VideoFavouriteColumns.TABLE_NAME} WHERE " +
            "videoId = :${DatabaseContract.VideoFavouriteColumns.VIDEO_ID} " +
            "AND videoType = :${DatabaseContract.VideoFavouriteColumns.VIDEO_TYPE}")
    fun deleteBasedIdAndVideoType(videoId: Long, videoType: String?): Int
}