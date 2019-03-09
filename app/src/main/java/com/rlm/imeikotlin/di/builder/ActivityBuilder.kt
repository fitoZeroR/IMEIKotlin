package com.rlm.imeikotlin.di.builder

import com.rlm.imeikotlin.di.PerActivity
import com.rlm.imeikotlin.ui.activity.enviarInformacion.EnviarInformacionActivity
import com.rlm.imeikotlin.ui.activity.enviarInformacion.EnviarInformacionActivityModule
import com.rlm.imeikotlin.ui.activity.login.LoginActivity
import com.rlm.imeikotlin.ui.activity.login.LoginActivityModule
import com.rlm.imeikotlin.ui.activity.main.MainActivity
import com.rlm.imeikotlin.ui.activity.main.MainActivityModule
import com.rlm.imeikotlin.ui.activity.main.fragment.directorio.DirectorioFragmentProvider
import com.rlm.imeikotlin.ui.activity.opciones.OpcionesActivity
import com.rlm.imeikotlin.ui.activity.opciones.OpcionesActivityModule
import com.rlm.imeikotlin.ui.activity.plantel.PlantelesActivity
import com.rlm.imeikotlin.ui.activity.plantel.PlantelesActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(LoginActivityModule::class))
    abstract fun bindLoginActivity(): LoginActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [OpcionesActivityModule::class])
    abstract fun bindOpcionesActivity(): OpcionesActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [EnviarInformacionActivityModule::class])
    abstract fun bindEnviarInformacionActivity(): EnviarInformacionActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(PlantelesActivityModule::class))
    abstract fun bindPlantelesActivity(): PlantelesActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, DirectorioFragmentProvider::class))
    abstract fun bindMainActivity(): MainActivity

    /*@ContributesAndroidInjector
    abstract fun contributeDescripcionActivity(): DescripcionActivity

    @ContributesAndroidInjector
    abstract fun contributeListaOpcionSeleccionadaActivity(): ListaOpcionSeleccionadaActivity

    @ContributesAndroidInjector
    abstract fun contributeListaOpcionSeleccionadaDiplomadosActivity(): ListaOpcionSeleccionadaDiplomadosActivity*/
}