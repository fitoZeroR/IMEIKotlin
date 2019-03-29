package com.rlm.imeikotlin.ui.activity.main.fragment.directorio

import android.app.Application
import com.rlm.imeikotlin.R
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DirectorioFragmentModule {
    @Provides
    @Named("ListaTelefonica")
    fun provideDirectorioTelefonico(application: Application) =
        application.resources.getStringArray(R.array.lista_telefono_directorio).toList()

    @Provides
    @Named("ListaPlantel")
    fun provideDirectorioPlantel(application: Application) =
        application.resources.getStringArray(R.array.lista_plantel_directorio).toList()
}