package com.rlm.imeikotlin.di.modules

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.PrettyFormatStrategy
import com.rlm.imeikotlin.ApplicationIMEI
import com.rlm.imeikotlin.data.remote.api.ImeiRemoteDataSource
import com.rlm.imeikotlin.data.remote.api.IRetrofitService
import com.rlm.imeikotlin.utils.ContextProviders
import dagger.Module
import javax.inject.Singleton
import dagger.Provides

@Module(includes = [RetrofitModule::class, RoomModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: ApplicationIMEI) = application

    /*@Provides
    @Singleton
    fun provideActivity(activity: Activity) = activity*/

    @Provides
    @Singleton
    fun provideLogAdapterConfig(): LogAdapter {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .methodOffset(3)
            .tag("Services")
            .build()
        return AndroidLogAdapter(formatStrategy)
    }

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Singleton
    @Provides
    fun provideGetCampusRemoteDataSource(iRetrofitService: IRetrofitService)
            = ImeiRemoteDataSource(iRetrofitService)

    @Singleton
    @Provides
    fun provideContextProviders(): ContextProviders
            = ContextProviders.getInstance()
}