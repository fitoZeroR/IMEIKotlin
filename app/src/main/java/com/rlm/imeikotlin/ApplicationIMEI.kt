package com.rlm.imeikotlin

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.Logger
import com.rlm.imeikotlin.di.component.AppComponent
import com.rlm.imeikotlin.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ApplicationIMEI : Application(), HasAndroidInjector {
    //class ApplicationIMEI : DaggerApplication() {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var logAdapter: LogAdapter

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
    /*override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: AppComponent by lazy {
            DaggerAppComponent
                .builder()
                .application(this)
                .build()
        }

        component.inject(this)

        return component
    }*/

    override fun onCreate() {
        super.onCreate()

        val component: AppComponent by lazy {
            DaggerAppComponent
                .builder()
                .application(this)
                .build()
        }

        component.inject(this)

        Logger.addLogAdapter(logAdapter)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}