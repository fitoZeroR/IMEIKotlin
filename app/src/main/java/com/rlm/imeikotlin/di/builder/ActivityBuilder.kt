package com.rlm.imeikotlin.di.builder

import com.rlm.imeikotlin.di.annotations.PerActivity
import com.rlm.imeikotlin.ui.activitys.planteles.PlantelesActivity
import com.rlm.imeikotlin.ui.activitys.enviarInformacion.EnviarInformacionActivity
import com.rlm.imeikotlin.ui.activitys.login.LoginActivity
import com.rlm.imeikotlin.ui.activitys.login.LoginActivityModule
import com.rlm.imeikotlin.ui.activitys.opciones.OpcionesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(LoginActivityModule::class))
    abstract fun bindLoginActivity(): LoginActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindOpcionesActivity(): OpcionesActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindEnviarInformacionActivity(): EnviarInformacionActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun bindPlantelesActivity(): PlantelesActivity

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