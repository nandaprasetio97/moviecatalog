package com.nandaprasetio.moviecatalog.presentation.adapter.listandloadstateadapter.listadapter.videolistadapter

import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.misc.extension.setImageWithGlide
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BaseListViewHolder
import com.nandaprasetio.moviecatalog.BuildConfig
import com.nandaprasetio.moviecatalog.databinding.LayoutItemVideoBinding

class VideoListViewHolder(
    private val layoutItemVideoBinding: LayoutItemVideoBinding
): BaseListViewHolder(layoutItemVideoBinding.root) {
    fun bind(onGetItem: () -> VideoAdapterData?, onGetItemPressed: () -> VideoAdapterData?, videoListAdapterOnPressed: VideoListAdapterOnPressed) {
        layoutItemVideoBinding.also {
            onGetItem()?.baseVideo?.also { it2 ->
                val limit = 120
                val tripleDots = if (it2.overview?.length ?: 0 > limit) { "..." } else { "" }
                it.imageViewAvatar.setImageWithGlide("${BuildConfig.IMAGE_URL_200}${it2.posterPath}")
                it.textViewName.text = it2.titleOrName
                it.textViewOverview.text = String.format("%s${tripleDots}", it2.overview?.take(limit))
                it.linearLayoutContainer.setOnClickListener { view ->
                    onGetItemPressed()?.baseVideo?.also { it3 ->
                        videoListAdapterOnPressed.invoke(it3, view.context)
                    }
                }
            }
        }
    }
}