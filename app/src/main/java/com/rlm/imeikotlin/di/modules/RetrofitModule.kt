package com.rlm.imeikotlin.di.modules

import android.app.Application
import com.rlm.imeikotlin.data.remote.api.LiveDataCallAdapterFactory
import com.rlm.imeikotlin.utils.APIConstants.URL
import com.rlm.imeikotlin.utils.DEBUG
import com.rlm.imeikotlin.data.remote.service.IRetrofitApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize = 25 * 1024 * 1024 // 25 MiB
        //int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level =
                if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, cache: Cache) = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .addInterceptor(httpLoggingInterceptor)
        .cache(cache)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApiWebservice(restAdapter: Retrofit): IRetrofitApi = restAdapter.create(IRetrofitApi::class.java)
}