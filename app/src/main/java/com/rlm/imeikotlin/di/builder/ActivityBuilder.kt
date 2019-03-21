package com.rlm.imeikotlin.di.builder

import com.rlm.imeikotlin.di.annotations.PerActivity
import com.rlm.imeikotlin.ui.login.LoginActivity
import com.rlm.imeikotlin.ui.login.LoginActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(LoginActivityModule::class))
    abstract fun bindLoginActivity(): LoginActivity

    /*@PerActivity
    @ContributesAndroidInjector(modules = [OpcionesActivityModule::class])
    abstract fun bindOpcionesActivity(): OpcionesActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [EnviarInformacionActivityModule::class])
    abstract fun bindEnviarInformacionActivity(): EnviarInformacionActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(PlantelesActivityModule::class))
    abstract fun bindPlantelesActivity(): PlantelesActivity*/

    /*@PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun bindMainActivity(): MainActivity*/

    /* Checar (Si no eliminar)

    @ContributesAndroidInjector
    abstract fun contributeDescripcionActivity(): DescripcionActivity

    @ContributesAndroidInjector
    abstract fun contributeListaOpcionSeleccionadaActivity(): ListaOpcionSeleccionadaActivity

    @ContributesAndroidInjector
    abstract fun contributeListaOpcionSeleccionadaDiplomadosActivity(): ListaOpcionSeleccionadaDiplomadosActivity*/
}