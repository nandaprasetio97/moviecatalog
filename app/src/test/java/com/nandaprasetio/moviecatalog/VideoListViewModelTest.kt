package com.nandaprasetio.moviecatalog

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nandaprasetio.core.data.database.AppDatabase
import com.nandaprasetio.core.data.listloaderdataargument.videolistloaderargument.*
import com.nandaprasetio.core.data.repository.VideoRepository
import com.nandaprasetio.core.di.databaseModule
import com.nandaprasetio.core.di.testingDatabaseModule
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.usecase.listloader.VideoListLoaderUseCase
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
class VideoListViewModelTest: KoinTest {
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val loadSize: Int = 20

    private val appDatabase: AppDatabase by inject()
    private val videoRepository: VideoRepository by inject()

    private lateinit var listVideoModel: ListViewModel<Int, VideoAdapterData>

    @Before
    fun init() {
        unloadKoinModules(databaseModule)
        loadKoinModules(testingDatabaseModule)
        initVideoFavouriteDataForTesting(videoRepository)
    }

    private inline fun<reified T: VideoListLoaderDataArgument> setListVideoModelBasedArgument(favourite: Boolean) {
        listVideoModel = ListViewModel(get<VideoListLoaderUseCase<T>>(named<T>()) { parametersOf(favourite) })
    }

    @Test
    fun getMovieVideo_isSuccess_hasItems() {
        setListVideoModelBasedArgument<MovieVideoListLoaderDataArgument>(false)
        assertSuccessAndHasItems(1..1)
    }

    @Test
    fun getMovieVideoFavourite_isSuccess_hasItems() {
        setListVideoModelBasedArgument<MovieVideoListLoaderDataArgument>(true)
        assertSuccessAndHasItems(1..1)
    }

    @Test
    fun getTvShowVideo_isSuccess_hasItems() {
        setListVideoModelBasedArgument<TvShowVideoListLoaderDataArgument>(false)
        assertSuccessAndHasItems(1..1)
    }

    @Test
    fun getTvShowVideoFavourite_isSuccess_hasItems() {
        setListVideoModelBasedArgument<TvShowVideoListLoaderDataArgument>(true)
        assertSuccessAndHasItems(1..1)
    }

    private fun assertSuccessAndHasItems(pageRange: IntRange) {
        runBlocking {
            pageRange.forEach { value ->
                listVideoModel.pagingSourceTesting(value, loadSize, {
                    assert(it.data.isNotEmpty()) { "Video list is empty." }
                }, {
                    throw it.throwable
                })
            }
        }
    }

    @After
    fun finish() {
        appDatabase.close()
        stopKoin()
    }
}