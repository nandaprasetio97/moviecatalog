package com.nandaprasetio.moviecatalog.presentation.activity.detailvideoactivity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.viewbinding.ViewBinding
import com.google.android.play.core.splitinstall.SplitInstallException
import com.nandaprasetio.moviecatalog.BuildConfig
import com.nandaprasetio.core.presentation.LoadingStateChangable
import com.nandaprasetio.moviecatalog.R
import com.nandaprasetio.moviecatalog.presentation.activity.BaseActivity
import com.nandaprasetio.moviecatalog.databinding.*
import com.nandaprasetio.core.presentation.errorprovider.BaseErrorProvider
import com.nandaprasetio.core.presentation.errorprovider.ErrorProviderResult
import com.nandaprasetio.core.misc.helper.DateHelper
import com.nandaprasetio.core.domain.entity.result.FailedHttpResult
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import com.nandaprasetio.core.domain.entity.video.*
import com.nandaprasetio.core.misc.extension.setImageWithGlide
import com.nandaprasetio.core.misc.extension.toThrowable
import com.nandaprasetio.core.presentation.viewmodel.DetailVideoViewModel
import com.nandaprasetio.moviecatalog.extension.installModule
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

abstract class BaseDetailVideoActivity<T: ViewBinding>(
    private val clazz: Class<T>
): BaseActivity<T>(), LoadingStateChangable {
    companion object {
        const val EXTRA_VIDEO: String = "extra.VIDEO"
    }

    private var nestedScrollViewDetailVideo: NestedScrollView? = null
    private var layoutViewDetailVideoBinding: LayoutViewDetailVideoBinding? = null
    private var layoutViewLoadingBinding: LayoutViewLoadingBinding? = null
    private var layoutViewNotificationBinding: LayoutViewNotificationBinding? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (this.intent.getParcelableExtra(EXTRA_VIDEO) as BaseVideo).let { baseVideo ->
            when {
                clazz.isAssignableFrom(ActivityDetailMovieVideoBinding::class.java) -> {
                    (this.getViewBinding() as ActivityDetailMovieVideoBinding?)?.also {
                        initializeViewField(it.layoutViewDetailVideo, it.layoutViewLoading, it.layoutViewNotification, it.nestedScrollViewDetailVideo)
                    }
                    getViewModel<DetailVideoViewModel<MovieVideo>>(named<MovieVideo>()) { parametersOf(baseVideo.id) }
                }
                clazz.isAssignableFrom(ActivityDetailTvShowVideoBinding::class.java) -> {
                    (this.getViewBinding() as ActivityDetailTvShowVideoBinding?)?.also {
                        initializeViewField(it.layoutViewDetailVideo, it.layoutViewLoading, it.layoutViewNotification, it.nestedScrollViewDetailVideo)
                    }
                    getViewModel<DetailVideoViewModel<TvShowVideo>>(named<TvShowVideo>()) { parametersOf(baseVideo.id) }
                }
                else -> throw IllegalStateException("Untuk inflate, view binding tidak ada yang sesuai.")
            }
        }.also {
            setDetailVideoViewModel(it)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun<A: BaseVideo> setDetailVideoViewModel(detailVideoViewModel: DetailVideoViewModel<A>) {
        detailVideoViewModel.videoLoadingStateLiveData.observe(this) {
            onLoadingStateChange(it)
        }
        detailVideoViewModel.videoLiveData.observe(this) {
            if (it is SuccessHttpResult) {
                bindLayoutViewDetailVideo(it.result) {
                    try {
                        it.result.video.also { video ->
                            installModule(
                                moduleName = "favourite",
                                context = this,
                                onIfModuleInstalled = { updateFavourite(video) },
                                onSuccessInstallModule = {
                                    updateFavourite(video)
                                    Toast.makeText(this, this.getText(R.string.success_load_module), Toast.LENGTH_LONG).show()
                                },
                                onFailedInstallModule = { e ->
                                    if (e is SplitInstallException) {
                                        Toast.makeText(this, "${this.getText(R.string.module_not_available)} (${e.errorCode})", Toast.LENGTH_LONG).show()
                                    }
                                }
                            )
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Favourite module belum dipasang.", Toast.LENGTH_LONG).show()
                    }
                }
            } else if (it is FailedHttpResult) {
                onLoadingFailed(BaseErrorProvider().getMessage(this, it.toThrowable()))
            }
        }
        detailVideoViewModel.videoFavouriteLiveData.observe(this) {
            if (it is SuccessHttpResult) {
                setColorFilterVideoFavourite(it.result)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.getBooleanExtra("extra.RESULT_FAVOURITE", false)?.also {
                setColorFilterVideoFavourite(it)
            }
        }
    }

    private fun<T: BaseVideo> updateFavourite(video: T) {
        Intent(
            this, Class.forName("com.nandaprasetio.moviecatalog.favourite.presentation.VideoFavouriteLikeActivity")
        ).apply {
            this.putExtra("extra.VIDEO", video as Parcelable)
            this@BaseDetailVideoActivity.startActivityForResult(this, 1)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onInitializeViewBinding(): T {
        return when {
            clazz.isAssignableFrom(ActivityDetailMovieVideoBinding::class.java) -> {
                ActivityDetailMovieVideoBinding.inflate(this.layoutInflater) as T
            }
            clazz.isAssignableFrom(ActivityDetailTvShowVideoBinding::class.java) -> {
                ActivityDetailTvShowVideoBinding.inflate(this.layoutInflater) as T
            }
            else -> throw IllegalStateException("View binding tidak ada yang sesuai.")
        }
    }

    private fun initializeViewField(
        layoutViewDetailVideoBinding: LayoutViewDetailVideoBinding,
        layoutViewLoadingBinding: LayoutViewLoadingBinding,
        layoutViewNotificationBinding: LayoutViewNotificationBinding,
        nestedScrollViewDetailVideo: NestedScrollView
    ) {
        this.layoutViewDetailVideoBinding = layoutViewDetailVideoBinding
        this.layoutViewLoadingBinding = layoutViewLoadingBinding
        this.layoutViewNotificationBinding = layoutViewNotificationBinding
        this.nestedScrollViewDetailVideo = nestedScrollViewDetailVideo
    }

    private fun bindLayoutViewDetailVideo(
        detailVideoWrapper: DetailVideoWrapper<*>,
        onImageViewFavouriteClick: () -> Unit
    ) {
        layoutViewDetailVideoBinding?.also {
            detailVideoWrapper.video.also { baseVideo ->
                it.textViewTitle.text = baseVideo.titleOrName
                it.textViewOriginalLanguange.text = baseVideo.originalLanguange
                it.imageViewAvatar.setImageWithGlide("${BuildConfig.IMAGE_URL_500}${baseVideo.posterPath}")
                it.imageViewFavourite.setOnClickListener {
                    onImageViewFavouriteClick()
                }
                it.textViewOverview.text = baseVideo.overview
                it.textViewPopularity.text = baseVideo.popularity.toString()
                it.textViewVoteAverage.text = baseVideo.voteAverage.toString()
                it.textViewVoteCount.text = baseVideo.voteCount.toString()
                setColorFilterVideoFavourite(detailVideoWrapper.isFavourited)

                if (baseVideo is DetailMovieVideo) {
                    it.textViewReleasedDate.text = DateHelper.formatDate(baseVideo.releaseDate)
                } else if (baseVideo is DetailTvShowVideo) {
                    it.textViewReleasedDate.text = this.getText(R.string.no_data)
                }
            }
        }
    }

    private fun setColorFilterVideoFavourite(favourited: Boolean) {
        layoutViewDetailVideoBinding?.also {
            it.imageViewFavourite.apply {
                this.setColorFilter(if (favourited) Color.RED else Color.GRAY)
                this.tag = if (favourited) 1 else 0
            }
        }
    }

    override fun onLoadingStateChange(state: Int) {
        nestedScrollViewDetailVideo?.visibility = if (state == 0) View.VISIBLE else View.INVISIBLE
        layoutViewLoadingBinding?.root?.visibility = if (state == -1) View.VISIBLE else View.INVISIBLE
        layoutViewNotificationBinding?.root?.visibility = if (state == -2) View.VISIBLE else View.INVISIBLE
    }

    override fun onLoadingFailed(errorProviderResult: ErrorProviderResult?) {
        layoutViewNotificationBinding?.textViewName?.text = errorProviderResult?.message
    }

    override fun onDestroy() {
        super.onDestroy()
        layoutViewLoadingBinding = null
        layoutViewNotificationBinding = null
    }
}