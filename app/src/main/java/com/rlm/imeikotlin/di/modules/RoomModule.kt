package com.rlm.imeikotlin.di.modules

import android.app.Application
import androidx.room.Room
import com.rlm.imeikotlin.data.local.DataBaseIMEI
import com.rlm.imeikotlin.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideDb(application: Application): DataBaseIMEI =
        Room.databaseBuilder(application.applicationContext, DataBaseIMEI::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    // Refactorizarlo
    @Singleton
    @Provides
    fun provideLoginDao(dataBaseIMEI: DataBaseIMEI) = dataBaseIMEI.alumnoDao()

    @Singleton
    @Provides
    fun provideOpcionDao(dataBaseIMEI: DataBaseIMEI) = dataBaseIMEI.opcionEstudioDao()

    @Singleton
    @Provides
    fun providePlantelDao(dataBaseIMEI: DataBaseIMEI) = dataBaseIMEI.plantelDao()

    @Singleton
    @Provides
    fun provideInformacionDao(dataBaseIMEI: DataBaseIMEI) = dataBaseIMEI.informacionDao()

    @Singleton
    @Provides
    fun provideDetalleAlumnoViewDao(dataBaseIMEI: DataBaseIMEI) = dataBaseIMEI.detalleAlumnoViewDao()
}