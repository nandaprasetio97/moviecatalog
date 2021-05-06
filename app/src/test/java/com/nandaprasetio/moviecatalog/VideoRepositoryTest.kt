package com.nandaprasetio.moviecatalog

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nandaprasetio.core.data.database.AppDatabase
import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.VideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.result.FailedHttpResult
import com.nandaprasetio.core.domain.entity.result.SuccessHttpResult
import com.nandaprasetio.core.domain.entity.video.BaseVideo
import com.nandaprasetio.core.domain.entity.video.MovieVideo
import com.nandaprasetio.core.domain.entity.video.TvShowVideo
import com.nandaprasetio.core.data.repository.VideoRepository
import com.nandaprasetio.core.di.databaseModule
import com.nandaprasetio.core.di.testingDatabaseModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P)
class VideoRepositoryTest: KoinTest {
    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val appDatabase: AppDatabase by inject()
    private val videoRepository: VideoRepository by inject()

    @Before
    fun init() {
        unloadKoinModules(databaseModule)
        loadKoinModules(testingDatabaseModule)
        initVideoFavouriteDataForTesting(videoRepository)
    }

    @Test
    fun getMovieVideo_isSuccess_hasItems() {
        runBlocking(Dispatchers.IO) {
            assertSuccessAndHasItems(videoRepository.getMovieVideoList(1))
        }
    }

    @Test
    fun getMovieVideoFavourite_isSuccess_hasItems() {
        runBlocking(Dispatchers.IO) {
            assertSuccessAndHasItems(videoRepository.getMovieVideoFavouriteList(1))
        }
    }

    @Test
    fun getDetailMovieVideo_isDetailVideoExists() {
        runBlocking(Dispatchers.IO) {
            assertTitleIsNotNullAndBlank(videoRepository.getMovieVideoList(1))
        }
    }

    @Test
    fun getDetailMovieVideo_setFavouriteValues_isFavouriteHasInvertValue() {
        updateVideoFavouriteAndAssertHasInvertValue(
            { videoRepository.getMovieVideoList(1) },
            { videoRepository.getMovieVideoIsFavourite(it.id) }
        )
    }

    @Test
    fun getTvShowVideo_isSuccess_hasItems() {
        runBlocking(Dispatchers.IO) {
            assertSuccessAndHasItems(videoRepository.getTvShowVideoList(1))
        }
    }

    @Test
    fun getTvShowVideoFavourite_isSuccess_hasItems() {
        runBlocking(Dispatchers.IO) {
            assertSuccessAndHasItems(videoRepository.getTvShowVideoFavouriteList(1))
        }
    }

    @Test
    fun getDetailTvShowVideo_isDetailVideoExists() {
        runBlocking(Dispatchers.IO) {
            assertTitleIsNotNullAndBlank(videoRepository.getTvShowVideoList(1))
        }
    }

    @Test
    fun getDetailTvShowVideo_setFavouriteValues_isFavouriteHasInvertValue() {
        updateVideoFavouriteAndAssertHasInvertValue(
            { videoRepository.getTvShowVideoList(1) },
            { videoRepository.getTvShowVideoIsFavourite(it.id) }
        )
    }

    private fun updateVideoFavouriteAndAssertHasInvertValue(
        getVideoList: suspend () -> BaseHttpResult<ListLoaderOutput<VideoAdapterData>>,
        getVideoIsFavourite: suspend (BaseVideo) -> BaseHttpResult<Boolean>
    ) {
        runBlocking(Dispatchers.IO) {
            assertSuccessAndHasItems(getVideoList()).also {
                it.result.resultList[0].baseVideo.also { it2 ->
                    val favouriteBefore: Boolean = assertAndGetBaseHttpResult(getVideoIsFavourite(it2))
                    val favouriteAfter: Boolean = when (it2) {
                        is MovieVideo -> assertAndGetBaseHttpResult(videoRepository.updateMovieVideoFavourite(it2))
                        is TvShowVideo -> assertAndGetBaseHttpResult(videoRepository.updateTvShowVideoFavourite(it2))
                        else -> throw AssertionError("Invalid video.")
                    }
                    assert(favouriteBefore != favouriteAfter)
                }
            }
        }
    }

    private fun assertSuccessAndHasItems(baseHttpResult: BaseHttpResult<ListLoaderOutput<VideoAdapterData>>): SuccessHttpResult<ListLoaderOutput<VideoAdapterData>> {
        assert(baseHttpResult is SuccessHttpResult)
        assert((baseHttpResult as SuccessHttpResult).result.resultList.isNotEmpty())
        return baseHttpResult
    }

    private fun assertTitleIsNotNullAndBlank(baseHttpResult: BaseHttpResult<ListLoaderOutput<VideoAdapterData>>) {
        assertSuccessAndHasItems(baseHttpResult).also {
            runBlocking {
                val detailVideoBaseHttpResult: BaseHttpResult<BaseVideo> = when (
                    val baseVideo: BaseVideo = it.result.resultList[0].baseVideo
                ) {
                    is MovieVideo -> videoRepository.getDetailMovieVideoList(baseVideo.id)
                    is TvShowVideo -> videoRepository.getDetailTvShowVideoList(baseVideo.id)
                    else -> throw AssertionError("Invalid video.")
                }
                assert(detailVideoBaseHttpResult is SuccessHttpResult) {
                    (detailVideoBaseHttpResult as FailedHttpResult).message ?: "No failed message."
                }
                assert(!((detailVideoBaseHttpResult as SuccessHttpResult).result.titleOrName.isNullOrBlank())) {
                    "Title or name is null or blank."
                }
            }
        }
    }

    private fun <T> assertAndGetBaseHttpResult(baseHttpResult: BaseHttpResult<T>): T {
        assert(baseHttpResult is SuccessHttpResult) {
            (baseHttpResult as FailedHttpResult).message ?: "No failed message."
        }
        return (baseHttpResult as SuccessHttpResult).result
    }

    @After
    fun finish() {
        appDatabase.close()
        stopKoin()
    }
}