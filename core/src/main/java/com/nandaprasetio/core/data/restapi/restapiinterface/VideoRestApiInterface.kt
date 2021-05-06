package com.nandaprasetio.core.data.restapi.restapiinterface

import com.nandaprasetio.core.BuildConfig
import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.MovieVideoAdapterData
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.TvShowVideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.DetailMovieVideo
import com.nandaprasetio.core.domain.entity.video.DetailTvShowVideo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VideoRestApiInterface {
    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getPopularMovieVideo(@Query("page") page: Int): Response<BaseHttpResult<ListLoaderOutput<MovieVideoAdapterData>>>

    @GET("movie/{movie_id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getDetailMovieVideo(@Path("movie_id") movieId: Int): Response<BaseHttpResult<DetailMovieVideo>>

    @GET("tv/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getPopularTvVideo(@Query("page") page: Int): Response<BaseHttpResult<ListLoaderOutput<TvShowVideoAdapterData>>>

    @GET("tv/{tv_id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getDetailTvVideo(@Path("tv_id") tvId: Int): Response<BaseHttpResult<DetailTvShowVideo>>
}