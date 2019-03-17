package com.rlm.imeikotlin.di.modules

import androidx.lifecycle.ViewModelProvider
import com.elcomercio.mvvm_dagger_kotlin.viewmodel.SampleViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(sampleViewModelFactory: SampleViewModelFactory):
            ViewModelProvider.Factory
}