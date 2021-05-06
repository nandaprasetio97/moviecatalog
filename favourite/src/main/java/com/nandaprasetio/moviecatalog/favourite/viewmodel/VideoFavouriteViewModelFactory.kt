package com.nandaprasetio.moviecatalog.favourite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.usecase.detailvideo.DetailVideoUseCase
import java.lang.IllegalStateException

@Suppress("UNCHECKED_CAST")
class VideoFavouriteViewModelFactory<T: BaseVideo>(
    private val clazz: Class<T>,
    private val videoId: Int,
    private val detailVideoUseCase: DetailVideoUseCase
): ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoFavouriteViewModel::class.java)) {
            return VideoFavouriteViewModel(clazz, videoId, detailVideoUseCase) as T
        }

        throw IllegalStateException("Must VideoFavouriteViewModel.")
    }
}