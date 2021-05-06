package com.nandaprasetio.moviecatalog.presentation.activity.detailvideoactivity

import android.os.Bundle
import com.nandaprasetio.moviecatalog.R
import com.nandaprasetio.moviecatalog.databinding.ActivityDetailMovieVideoBinding

class MovieDetailVideoActivity: BaseDetailVideoActivity<ActivityDetailMovieVideoBinding>(ActivityDetailMovieVideoBinding::class.java) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.setTitle(R.string.detail_movie)
    }
}