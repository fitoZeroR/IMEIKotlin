package com.rlm.imeikotlin.ui.activitys

import android.os.Bundle
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.ui.adapters.CustomAdapterOpciones
import com.rlm.imeikotlin.utils.BUNDLE_NOMBRE_OPCION
import com.rlm.imeikotlin.utils.BUNDLE_OPCION_SELECCIONADA
import com.rlm.imeikotlin.utils.EListado
import kotlinx.android.synthetic.main.activity_opciones.*
import java.util.*

class ListaOpcionSeleccionadaDiplomadosActivity : BaseActivity() {
    private lateinit var listaOpcionDiplomados: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        intent.extras.let {
            listaOpcionDiplomados = it.getStringArrayList(BUNDLE_OPCION_SELECCIONADA)
            title = it.getString(BUNDLE_NOMBRE_OPCION)
        }

        llamaAdaptador()
    }

    override fun getLayoutResource() = R.layout.activity_opciones

    private fun llamaAdaptador() {
        rcv_opciones_id.also {
            it.adapter = CustomAdapterOpciones(listaOpcionDiplomados, null, EListado.SELECCIONA_OPCION_DIPLOMADO) {}
        }
    }
}