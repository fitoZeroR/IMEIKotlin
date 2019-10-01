package com.rlm.imeikotlin.ui.activitys.main.fragment.directorio

import android.app.Application
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.utils.NAMED_LISTA_PLANTEL
import com.rlm.imeikotlin.utils.NAMED_LISTA_TELEFONICA
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DirectorioFragmentModule {
    @Provides
    @Named(NAMED_LISTA_TELEFONICA)
    fun provideDirectorioTelefonico(application: Application) =
        application.resources.getStringArray(R.array.lista_telefono_directorio).toList()

    @Provides
    @Named(NAMED_LISTA_PLANTEL)
    fun provideDirectorioPlantel(application: Application) =
        application.resources.getStringArray(R.array.lista_plantel_directorio).toList()
}