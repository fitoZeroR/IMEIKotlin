package com.rlm.imeikotlin.ui.activity.main.fragment.directorio

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DirectorioFragmentModule {
    @Provides
    //@Named("Fragment")
    fun provideStringAutor() = "Rodolfo Luis Martinez"
}