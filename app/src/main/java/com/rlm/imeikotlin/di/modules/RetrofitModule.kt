package com.rlm.imeikotlin.di.modules

import android.app.Application
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.LiveDataCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.rlm.imeikotlin.repository.modelo.*
import com.rlm.imeikotlin.utils.APIConstants.URL
import com.rlm.imeikotlin.utils.DEBUG
import com.rlm.imeikotlin.repository.api.IRetrofitApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {
    // --- NETWORK INJECTION ---

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        val cacheSize = 25 * 1024 * 1024 // 25 MiB
        //int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideInterceptor() = HttpLoggingInterceptor().setLevel(if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

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
    fun provideGson() = GsonBuilder()
            .setLenient()
            .registerTypeAdapter(Login::class.java, JsonDeserializer { json, typeOfT, context -> Gson().fromJson(json, Login::class.java) })
            .registerTypeAdapter(RecuperarPassword::class.java, JsonDeserializer({ json, typeOfT, context -> Gson().fromJson(json, RecuperarPassword::class.java) }))
            .registerTypeAdapter(PagosAsignaturas::class.java, JsonDeserializer({ json, typeOfT, context -> Gson().fromJson(json, PagosAsignaturas::class.java) }))
            .registerTypeAdapter(Opciones::class.java, JsonDeserializer({ json, typeOfT, context -> Gson().fromJson(json, Opciones::class.java) }))
            .registerTypeAdapter(InformacionPlanteles::class.java, JsonDeserializer({ json, typeOfT, context -> Gson().fromJson(json, InformacionPlanteles::class.java) }))
            .registerTypeAdapter(EnviarInformacion::class.java, JsonDeserializer({ json, typeOfT, context -> Gson().fromJson(json, EnviarInformacion::class.java) }))
            .registerTypeAdapter(Foto::class.java, JsonDeserializer({ json, typeOfT, context -> Gson().fromJson(json, Foto::class.java) }))
            .registerTypeAdapter(DescargaBoleta::class.java, JsonDeserializer({ json, typeOfT, context -> Gson().fromJson(json, DescargaBoleta::class.java) }))
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideApiWebservice(restAdapter: Retrofit): IRetrofitApi {
        return restAdapter.create(IRetrofitApi::class.java)
    }
}