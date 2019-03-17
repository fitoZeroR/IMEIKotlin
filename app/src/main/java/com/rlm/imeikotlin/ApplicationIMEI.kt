package com.rlm.imeikotlin

import android.content.Context
import androidx.multidex.MultiDex
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.Logger
import com.rlm.imeikotlin.di.component.AppComponent
import com.rlm.imeikotlin.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class ApplicationIMEI : DaggerApplication() {
    @Inject
    lateinit var logAdapter: LogAdapter

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: AppComponent by lazy {
            DaggerAppComponent
                .builder()
                .application(this)
                .build()
        }

        component.inject(this)

        return component
    }

    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(logAdapter)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}