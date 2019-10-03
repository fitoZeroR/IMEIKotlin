package com.rlm.imeikotlin.ui.activitys.main.fragment.directorio

import com.rlm.imeikotlin.di.annotations.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class DirectorioFragmentProvider {
    @FragmentScoped
    @ContributesAndroidInjector(modules = [DirectorioFragmentModule::class])
    abstract fun provideDirectorioFragmentFactory(): DirectorioFragment
}