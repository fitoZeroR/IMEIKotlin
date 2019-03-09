package com.rlm.imeikotlin.ui.activity.main.fragment.directorio

import com.rlm.imeikotlin.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DirectorioFragmentProvider {
    @FragmentScoped
    @ContributesAndroidInjector(modules = [DirectorioFragmentModule::class])
    abstract fun provideDirectorioFragmentFactory(): DirectorioFragment
}