@file:Suppress("unused")

package com.nandaprasetio.moviecatalog.favourite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import com.nandaprasetio.core.domain.usecase.detailvideo.DetailVideoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoFavouriteViewModel<T: BaseVideo>(
    private val clazz: Class<T>,
    videoId: Int,
    private val detailVideoUseCase: DetailVideoUseCase
): ViewModel() {
    val videoFavouriteLiveData: LiveData<BaseHttpResult<Boolean>> = MutableLiveData()
    private val videoFavouriteMutableLiveData: MutableLiveData<BaseHttpResult<Boolean>>
        = videoFavouriteLiveData as MutableLiveData

    fun updateVideoFavourite(baseVideo: T) {
        viewModelScope.launch(Dispatchers.IO) {
            videoFavouriteMutableLiveData.postValue(
                when (baseVideo) {
                    is MovieVideo -> detailVideoUseCase.updateMovieVideoFavourite(baseVideo)
                    is TvShowVideo -> detailVideoUseCase.updateTvShowVideoFavourite(baseVideo)
                    else -> throw IllegalStateException("Invalid class.")
                }
            )
        }
    }
}