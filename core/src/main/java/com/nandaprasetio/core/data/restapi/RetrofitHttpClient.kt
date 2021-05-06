package com.nandaprasetio.core.data.restapi

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.nandaprasetio.core.BuildConfig
import com.nandaprasetio.core.domain.entity.ListLoaderOutput
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.MovieVideoAdapterData
import com.nandaprasetio.core.domain.entity.adapterdata.videoadapterdata.TvShowVideoAdapterData
import com.nandaprasetio.core.domain.entity.result.BaseHttpResult
import com.nandaprasetio.core.domain.entity.video.DetailMovieVideo
import com.nandaprasetio.core.domain.entity.video.DetailTvShowVideo
import com.nandaprasetio.core.data.restapi.jsondeserializer.DetailMovieVideoJsonDeserializer
import com.nandaprasetio.core.data.restapi.jsondeserializer.DetailTvShowVideoJsonDeserializer
import com.nandaprasetio.core.data.restapi.jsondeserializer.MovieVideoJsonDeserializer
import com.nandaprasetio.core.data.restapi.jsondeserializer.TvShowVideoJsonDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHttpClient {
    companion object {
        @Volatile
        private var retrofit: Retrofit? = null

        @JvmStatic
        fun of(): Retrofit {
            if (retrofit == null) {
                retrofit = initRetrofit()
            }
            return retrofit!!
        }

        private fun initRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(initGson()))
                .build()
        }

        private fun initGson(): Gson {
            return GsonBuilder().setLenient()
                .registerTypeAdapter(object: TypeToken<@JvmSuppressWildcards BaseHttpResult<@JvmSuppressWildcards ListLoaderOutput<MovieVideoAdapterData>>>(){}.type, MovieVideoJsonDeserializer())
                .registerTypeAdapter(object: TypeToken<@JvmSuppressWildcards BaseHttpResult<@JvmSuppressWildcards DetailMovieVideo>>(){}.type, DetailMovieVideoJsonDeserializer())
                .registerTypeAdapter(object: TypeToken<@JvmSuppressWildcards BaseHttpResult<@JvmSuppressWildcards ListLoaderOutput<TvShowVideoAdapterData>>>(){}.type, TvShowVideoJsonDeserializer())
                .registerTypeAdapter(object: TypeToken<@JvmSuppressWildcards BaseHttpResult<@JvmSuppressWildcards DetailTvShowVideo>>(){}.type, DetailTvShowVideoJsonDeserializer())
                .create()
        }
    }
}