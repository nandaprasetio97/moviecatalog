@file:Suppress("unused")

package com.nandaprasetio.core.data

import androidx.room.ColumnInfo

class VideoFavouriteCount(
    @ColumnInfo(name = "count") val count: Int,
)