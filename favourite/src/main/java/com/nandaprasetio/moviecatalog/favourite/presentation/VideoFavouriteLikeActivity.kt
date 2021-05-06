@file:Suppress("unused")

package com.nandaprasetio.moviecatalog.favourite.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import com.nandaprasetio.moviecatalog.favourite.viewmodel.VideoFavouriteViewModel
import com.nandaprasetio.moviecatalog.favourite.viewmodel.VideoFavouriteViewModelFactory
import org.koin.android.ext.android.get

class VideoFavouriteLikeActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_VIDEO = "extra.VIDEO"
        const val EXTRA_RESULT_FAVOURITE = "extra.RESULT_FAVOURITE"
        const val REQUEST_CODE_VIDEO_FAVOURITE_LIKE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setFavouriteViewModel((this.intent.getParcelableExtra(EXTRA_VIDEO) as BaseVideo?)).also {
                it.videoFavouriteLiveData.observe(this) {
                    this.setResult(Activity.RESULT_OK, Intent().apply {
                        this.putExtra(EXTRA_RESULT_FAVOURITE, if (it is SuccessHttpResult) { it.result } else { false })
                    })
                    this.finish()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            this.finish()
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun setFavouriteViewModel(baseVideo: BaseVideo?): VideoFavouriteViewModel<*> {
        return when (baseVideo) {
            is MovieVideo -> {
                (ViewModelProvider(this@VideoFavouriteLikeActivity, VideoFavouriteViewModelFactory(
                    MovieVideo::class.java, baseVideo.id, get()
                )).get(VideoFavouriteViewModel::class.java) as VideoFavouriteViewModel<MovieVideo>).also {
                    it.updateVideoFavourite(baseVideo)
                }
            }
            is TvShowVideo -> {
                (ViewModelProvider(this@VideoFavouriteLikeActivity, VideoFavouriteViewModelFactory(
                    TvShowVideo::class.java, baseVideo.id, get()
                )).get(VideoFavouriteViewModel::class.java) as VideoFavouriteViewModel<TvShowVideo>).also {
                    it.updateVideoFavourite(baseVideo)
                }
            }
            else -> throw IllegalStateException("Invalid video")
        }
    }
}