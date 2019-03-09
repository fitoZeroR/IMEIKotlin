package com.rlm.imeikotlin.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.ui.adapter.CustomAdapterOpciones
import com.rlm.imeikotlin.utils.BUNDLE_NOMBRE_OPCION
import com.rlm.imeikotlin.utils.BUNDLE_OPCION_SELECCIONADA
import com.rlm.imeikotlin.utils.EListado
import kotlinx.android.synthetic.main.activity_opciones.*
import java.util.*

class ListaOpcionSeleccionadaDiplomadosActivity : BaseActivity() {
    private var listaOpcionDiplomados: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        val bundleExtras = intent.extras
        if (bundleExtras != null) {
            listaOpcionDiplomados = bundleExtras.getStringArrayList(BUNDLE_OPCION_SELECCIONADA)
            //Log.i("RLM", "Tamaño = " + listaOpcionDiplomados.size());
            title = bundleExtras.getString(BUNDLE_NOMBRE_OPCION)
        }

        llamaAdaptador()
    }

    private fun llamaAdaptador() {
        // Llama Adaptador
        rcv_opciones_id.layoutManager = LinearLayoutManager(this)
        rcv_opciones_id.setHasFixedSize(true)
        rcv_opciones_id.swapAdapter(CustomAdapterOpciones(listaOpcionDiplomados, null, EListado.SELECCIONA_OPCION_DIPLOMADO) {

        }, true)
    }

    override fun getLayoutResource() = R.layout.activity_opciones
}
