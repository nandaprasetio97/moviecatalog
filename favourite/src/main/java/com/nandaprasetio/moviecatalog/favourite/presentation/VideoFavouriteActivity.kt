package com.nandaprasetio.moviecatalog.favourite.presentation

import android.os.Bundle
import com.nandaprasetio.moviecatalog.R
import com.nandaprasetio.core.presentation.RefreshWhileResuming
import com.nandaprasetio.moviecatalog.presentation.activity.VideoListActivity

class VideoFavouriteActivity: VideoListActivity(
    favourite = true,
    enableBack = true
), RefreshWhileResuming {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.setTitle(R.string.favourite)
    }
}