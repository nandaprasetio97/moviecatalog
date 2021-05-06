package com.nandaprasetio.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nandaprasetio.core.data.database.dao.VideoFavouriteDao
import com.nandaprasetio.core.domain.entity.VideoFavourite

@Database(entities = [VideoFavourite::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun videoFavouriteDao(): VideoFavouriteDao
}