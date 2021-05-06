@file:Suppress("unused")

package com.nandaprasetio.core.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaprasetio.core.domain.usecase.detailvideo.DetailVideoUseCase
import com.nandaprasetio.core.misc.extension.changeStateBaseHttpResult
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.entity.video.DetailVideoWrapper
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailVideoViewModel<T: BaseVideo>(
    private val clazz: Class<T>,
    videoId: Int,
    private val detailVideoUseCase: DetailVideoUseCase
): ViewModel() {
    val videoLiveData: LiveData<BaseHttpResult<DetailVideoWrapper<T>>> = MutableLiveData()
    private val videoMutableLiveData: MutableLiveData<BaseHttpResult<DetailVideoWrapper<T>>>
        = videoLiveData as MutableLiveData

    val videoFavouriteLiveData: LiveData<BaseHttpResult<Boolean>> = MutableLiveData()
    private val videoFavouriteMutableLiveData: MutableLiveData<BaseHttpResult<Boolean>>
        = videoFavouriteLiveData as MutableLiveData

    val videoLoadingStateLiveData: LiveData<Int> = MutableLiveData()
    private val videoLoadingStateMutableLiveData: MutableLiveData<Int>
        = videoLoadingStateLiveData as MutableLiveData

    init {
        loadVideoRepository(videoId)
    }

    @Suppress("UNCHECKED_CAST")
    private fun loadVideoRepository(videoId: Int) {
        videoLoadingStateMutableLiveData.value = -1
        viewModelScope.launch(Dispatchers.IO) {
            val baseHttpResult: BaseHttpResult<DetailVideoWrapper<T>> = when {
                clazz.isAssignableFrom(MovieVideo::class.java) -> {
                    detailVideoUseCase.getDetailMovieVideoList(videoId) as BaseHttpResult<DetailVideoWrapper<T>>
                }
                clazz.isAssignableFrom(TvShowVideo::class.java) -> {
                    detailVideoUseCase.getDetailTvShowVideoList(videoId) as BaseHttpResult<DetailVideoWrapper<T>>
                }
                else -> throw IllegalStateException("Invalid class.")
            }
            videoMutableLiveData.postValue(baseHttpResult)
            changeStateBaseHttpResult(
                videoLoadingStateMutableLiveData,
                baseHttpResult
            )
        }
    }

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