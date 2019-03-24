package com.rlm.imeikotlin.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elcomercio.mvvm_dagger_kotlin.viewmodel.ViewModelFactoryIMEI
import com.rlm.imeikotlin.di.annotations.ViewModelKey
import com.rlm.imeikotlin.ui.activitys.enviarInformacion.EnviarInformacionViewModel
import com.rlm.imeikotlin.ui.activitys.login.LoginViewModel
import com.rlm.imeikotlin.ui.activitys.opciones.OpcionesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OpcionesViewModel::class)
    abstract fun bindOpcionesViewModel(opcionesViewModel: OpcionesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EnviarInformacionViewModel::class)
    abstract fun bindEnviarInformacionViewModel(enviarInformacionViewModel: EnviarInformacionViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactoryIMEI: ViewModelFactoryIMEI): ViewModelProvider.Factory
}