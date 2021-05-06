package com.nandaprasetio.moviecatalog

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nandaprasetio.core.data.database.AppDatabase
import com.nandaprasetio.core.misc.extension.getOrAwaitValue
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.*
import com.nandaprasetio.core.di.databaseModule
import com.nandaprasetio.core.di.testingDatabaseModule
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import com.nandaprasetio.core.domain.usecase.listloader.VideoListLoaderUseCase
import com.nandaprasetio.core.presentation.viewmodel.DetailVideoViewModel
import com.nandaprasetio.core.presentation.viewmodel.ListViewModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P)
class DetailVideoViewModelTest: KoinTest {
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val appDatabase: AppDatabase by inject()
    private lateinit var detailVideoViewModel: DetailVideoViewModel<*>

    @Before
    fun init() {
        unloadKoinModules(databaseModule)
        loadKoinModules(testingDatabaseModule)
    }

    @Test
    fun getDetailMovieVideo_isDetailVideoExists() {
        assertIsDetailVideoExists(
            BaseVideo::class.java
        )
    }

    @Test
    fun getDetailTvShowVideo_isDetailVideoExists() {
        assertIsDetailVideoExists(
            BaseVideo::class.java
        )
    }

    private fun<T: BaseVideo> assertIsDetailVideoExists(baseVideoClazz: Class<T>) {
        ListViewModel(
            when {
                baseVideoClazz.isAssignableFrom(MovieVideo::class.java) -> {
                    get<VideoListLoaderUseCase<MovieVideoListLoaderDataArgument>>(named<MovieVideoListLoaderDataArgument>()) { parametersOf(false) }
                }
                baseVideoClazz.isAssignableFrom(TvShowVideo::class.java) -> {
                    get<VideoListLoaderUseCase<TvShowVideoListLoaderDataArgument>>(named<TvShowVideoListLoaderDataArgument>()) { parametersOf(false) }
                }
                else -> throw IllegalStateException("Invalid video")
            }
        ).also { listViewModel ->
            runBlocking {
                listViewModel.pagingSourceTesting(1, 20, {
                    assertingIsDetailVideoExists(it.data[0].baseVideo.id, baseVideoClazz)
                }, {
                    throw it.throwable
                })
            }
        }
    }

    private fun<T: BaseVideo> assertingIsDetailVideoExists(videoId: Int, baseVideoClazz: Class<T>) {
        detailVideoViewModel = DetailVideoViewModel(baseVideoClazz, videoId, get())
        assert(detailVideoViewModel.videoLiveData.getOrAwaitValue().let {
            assert(it is SuccessHttpResult)
            !(it as SuccessHttpResult).result.video.titleOrName.isNullOrBlank()
        })
    }

    @After
    fun finish() {
        appDatabase.close()
        stopKoin()
    }
}