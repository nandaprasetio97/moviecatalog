package com.nandaprasetio.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.nandaprasetio.core.BuildConfig
import com.nandaprasetio.core.data.restapi.jsondeserializer.DetailMovieVideoJsonDeserializer
import com.nandaprasetio.core.data.restapi.jsondeserializer.DetailTvShowVideoJsonDeserializer
import com.nandaprasetio.core.data.restapi.jsondeserializer.MovieVideoJsonDeserializer
import com.nandaprasetio.core.data.restapi.jsondeserializer.TvShowVideoJsonDeserializer
import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.MovieVideoAdapterData
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.TvShowVideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.DetailMovieVideo
import com.nandaprasetio.core.domain.entity.video.DetailTvShowVideo
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule: Module = module {
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(initGson()))
            .client(get())
            .build()
    }
    single {
        val certificatePinner = CertificatePinner.Builder()
            .add(BuildConfig.BASE_URL, "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
            .add(BuildConfig.BASE_URL, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(BuildConfig.BASE_URL, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .add(BuildConfig.BASE_URL, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
            .build()
        OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()
    }
}

private fun initGson(): Gson {
    return GsonBuilder().setLenient()
        .registerTypeAdapter(object: TypeToken<@JvmSuppressWildcards BaseHttpResult<@JvmSuppressWildcards ListLoaderOutput<MovieVideoAdapterData>>>(){}.type, MovieVideoJsonDeserializer())
        .registerTypeAdapter(object: TypeToken<@JvmSuppressWildcards BaseHttpResult<@JvmSuppressWildcards DetailMovieVideo>>(){}.type, DetailMovieVideoJsonDeserializer())
        .registerTypeAdapter(object: TypeToken<@JvmSuppressWildcards BaseHttpResult<@JvmSuppressWildcards ListLoaderOutput<TvShowVideoAdapterData>>>(){}.type, TvShowVideoJsonDeserializer())
        .registerTypeAdapter(object: TypeToken<@JvmSuppressWildcards BaseHttpResult<@JvmSuppressWildcards DetailTvShowVideo>>(){}.type, DetailTvShowVideoJsonDeserializer())
        .create()
}