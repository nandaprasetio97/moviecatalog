@file:Suppress("unused")

package com.nandaprasetio.moviecatalog.presentation.adapter.listandloadstateadapter.listadapter.videolistadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BaseListAdapter
import com.nandaprasetio.moviecatalog.databinding.LayoutItemVideoBinding

class VideoListAdapter(
    private val videoAdapterDataList: List<VideoAdapterData>,
    private val videoListAdapterOnPressed: VideoListAdapterOnPressed
): BaseListAdapter<VideoListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {
        return VideoListViewHolder(
            LayoutItemVideoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return videoAdapterDataList.size
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        holder.bind(
            {videoAdapterDataList[holder.bindingAdapterPosition]},
            {videoAdapterDataList[holder.layoutPosition]},
            videoListAdapterOnPressed
        )
    }
}