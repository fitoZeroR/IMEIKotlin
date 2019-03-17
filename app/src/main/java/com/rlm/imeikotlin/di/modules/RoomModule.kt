package com.rlm.imeikotlin.di.modules

import android.app.Application
import androidx.room.Room
import com.rlm.imeikotlin.repository.local.MyDataBase
import com.rlm.imeikotlin.utils.AppExecutors
import com.rlm.imeikotlin.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDb(application: Application): MyDataBase =
            Room.databaseBuilder(application.applicationContext, MyDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

    /*@Singleton
    @Provides
    fun abstract provideUserDao(myDataBase: MyDataBase) = myDataBase.userDao()*/

    @Singleton
    @Provides
    fun provideAppExecutors() = AppExecutors(Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT), AppExecutors.MainThreadExecutor())

    companion object {
        const val THREAD_COUNT = 3
    }
}