package com.nandaprasetio.moviecatalog.presentation.adapter.listandloadstateadapter.listadapter.videolistadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BasePagingDataAdapter
import com.nandaprasetio.moviecatalog.databinding.LayoutItemVideoBinding

class VideoPagingDataAdapter(
    private val videoListAdapterOnPressed: VideoListAdapterOnPressed
): BasePagingDataAdapter<VideoAdapterData, VideoListViewHolder>(
    VideoDiffUtilCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        return VideoListViewHolder(
            LayoutItemVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.bind(
            {this@VideoPagingDataAdapter.getItem(holder.bindingAdapterPosition)},
            {this@VideoPagingDataAdapter.getItem(holder.layoutPosition)},
            videoListAdapterOnPressed
        )
    }
}