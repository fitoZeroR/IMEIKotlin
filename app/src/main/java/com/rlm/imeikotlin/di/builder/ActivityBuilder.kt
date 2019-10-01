package com.rlm.imeikotlin.di.builder

import com.rlm.imeikotlin.di.annotations.PerActivity
import com.rlm.imeikotlin.ui.activitys.main.fragment.directorio.DirectorioFragmentProvider
import com.rlm.imeikotlin.ui.activitys.main.MainActivity
import com.rlm.imeikotlin.ui.activitys.planteles.PlantelesActivity
import com.rlm.imeikotlin.ui.activitys.enviarInformacion.EnviarInformacionActivity
import com.rlm.imeikotlin.ui.activitys.login.LoginActivity
import com.rlm.imeikotlin.ui.activitys.main.MainActivityModule
import com.rlm.imeikotlin.ui.activitys.opciones.OpcionesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {
    @PerActivity
    @ContributesAndroidInjector
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

    @PerActivity
    @ContributesAndroidInjector(modules = [MainActivityModule::class, DirectorioFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity
}