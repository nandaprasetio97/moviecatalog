package com.nandaprasetio.moviecatalog.presentation.adapter.listandloadstateadapter.listadapter.videolistadapter

import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BaseDiffUtilItemCallback


class VideoDiffUtilCallback: BaseDiffUtilItemCallback<VideoAdapterData>() {
    override fun areItemsTheSame(oldItem: VideoAdapterData, newItem: VideoAdapterData): Boolean {
        return oldItem.baseVideo.id == newItem.baseVideo.id
    }

    override fun areContentsTheSame(oldItem: VideoAdapterData, newItem: VideoAdapterData): Boolean {
        val oldBaseVideo: BaseVideo = oldItem.baseVideo
        val newBaseVideo: BaseVideo = newItem.baseVideo
        return when {
            oldBaseVideo is TvShowVideo && newBaseVideo is TvShowVideo -> {
                oldBaseVideo == newBaseVideo
            }
            oldBaseVideo is MovieVideo && newBaseVideo is MovieVideo -> {
                oldBaseVideo == newBaseVideo
            }
            else -> false
        }
    }
}