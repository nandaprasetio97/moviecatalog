package com.nandaprasetio.moviecatalog.presentation.fragment.recyclerviewfragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nandaprasetio.moviecatalog.presentation.activity.detailvideoactivity.BaseDetailVideoActivity
import com.nandaprasetio.moviecatalog.presentation.activity.detailvideoactivity.MovieDetailVideoActivity
import com.nandaprasetio.moviecatalog.presentation.activity.detailvideoactivity.TvShowDetailVideoActivity
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BaseListViewHolder
import com.nandaprasetio.core.presentation.adapter.listandloadstateadapter.listadapter.baselistadapter.BasePagingDataAdapter
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.MovieVideoListLoaderDataArgument
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.VideoListLoaderDataArgument
import com.nandaprasetio.core.domain.usecase.listloader.VideoListLoaderUseCase
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import com.nandaprasetio.moviecatalog.presentation.adapter.listandloadstateadapter.listadapter.videolistadapter.VideoPagingDataAdapter

abstract class BaseVideoRecyclerViewFragment<T: VideoListLoaderDataArgument>(
    key: String?
): BaseRecyclerViewFragment<VideoAdapterData, VideoListLoaderUseCase<MovieVideoListLoaderDataArgument>>(
    key = key,
) {
    companion object {
        const val ARGUMENT_FAVOURITE: String = "argument.FAVOURITE"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewResult: View? = super.onCreateView(inflater, container, savedInstanceState)
        listLoaderMediator.getList()
        return viewResult
    }

    protected fun isFavourite(): Boolean {
        (this.arguments?.get(ARGUMENT_FAVOURITE) as Boolean?).let {
            return it ?: false
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getPagingListAdapter(): BasePagingDataAdapter<VideoAdapterData, BaseListViewHolder> {
        return VideoPagingDataAdapter { baseVideo, context ->
            context.startActivity(getIntent(baseVideo, context))
        } as BasePagingDataAdapter<VideoAdapterData, BaseListViewHolder>
    }

    private fun getIntent(baseVideo: BaseVideo, context: Context): Intent {
        val applyIntent: Intent.() -> Unit = {
            this.putExtra(BaseDetailVideoActivity.EXTRA_VIDEO, baseVideo as Parcelable)
        }
        return when (baseVideo) {
            is MovieVideo -> Intent(context, MovieDetailVideoActivity::class.java).apply(applyIntent)
            is TvShowVideo -> Intent(context, TvShowDetailVideoActivity::class.java).apply(applyIntent)
            else -> throw IllegalStateException("Tipe base video harus mencakup turunan.")
        }
    }
}