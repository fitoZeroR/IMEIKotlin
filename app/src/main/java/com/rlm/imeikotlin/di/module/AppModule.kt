package com.rlm.imeikotlin.di.module

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.PrettyFormatStrategy
import com.rlm.imeikotlin.ApplicationIMEI
import dagger.Module
import javax.inject.Singleton
import dagger.Provides

@Module(includes = [RetrofitModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideContext(application: ApplicationIMEI) = application

    @Provides
    @Singleton
    internal fun provideActivity(activity: Activity) = activity

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

    /*@Provides
    @Singleton
    internal fun provideFirebaseAnalytics(): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(provideImeiApplicationContext())
    }*/
}