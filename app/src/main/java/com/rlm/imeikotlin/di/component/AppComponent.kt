package com.rlm.imeikotlin.di.component

import android.app.Application
import com.rlm.imeikotlin.ApplicationIMEI
import com.rlm.imeikotlin.di.PerActivity
import com.rlm.imeikotlin.di.builder.ActivityBuilder
import com.rlm.imeikotlin.di.module.AppModule
import com.rlm.imeikotlin.di.module.RetrofitModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

//@PerActivity
@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivityBuilder::class, AppModule::class])
//interface AppComponent {
interface AppComponent : AndroidInjector<DaggerApplication> {
    fun inject(app: ApplicationIMEI)
    //override fun inject(app: ApplicationIMEI)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}