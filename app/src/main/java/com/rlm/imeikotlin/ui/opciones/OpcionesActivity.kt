package com.rlm.imeikotlin.ui.opciones

import android.os.Bundle
import androidx.lifecycle.Observer
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.ui.BaseActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class OpcionesActivity : BaseActivity() {
    @Inject
    lateinit var opcionesViewModel: OpcionesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        subscribeToOpcionModel()

        opcionesViewModel.loadAllOptions(true)
    }

    override fun getLayoutResource() = R.layout.activity_opciones

    private fun subscribeToOpcionModel() {
        opcionesViewModel.getAllOptionsResourceLiveData.observe(this, Observer {

        })
    }
}