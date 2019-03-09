package com.rlm.imeikotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.modelo.Grado
import com.rlm.imeikotlin.ui.adapter.CustomAdapterOpciones
import com.rlm.imeikotlin.utils.BUNDLE_DESCRIPCION
import com.rlm.imeikotlin.utils.BUNDLE_NOMBRE_OPCION
import com.rlm.imeikotlin.utils.BUNDLE_OPCION_SELECCIONADA
import com.rlm.imeikotlin.utils.EListado
import kotlinx.android.synthetic.main.activity_opciones.*
import java.util.*

class ListaOpcionSeleccionadaActivity : BaseActivity() {
    private var gradoList: List<Grado>? = null
    private var titulo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        val bundleExtras = intent.extras
        if (bundleExtras != null) {
            val opcionSeleccionada = bundleExtras.getString(BUNDLE_OPCION_SELECCIONADA)
            val typeOfObjectsList = object : TypeToken<ArrayList<Grado>>() {}.type
            gradoList = Gson().fromJson<List<Grado>>(opcionSeleccionada, typeOfObjectsList)

            titulo = bundleExtras.getString(BUNDLE_NOMBRE_OPCION)
            setTitle(titulo)
        }

        llamaAdaptdor()
    }

    override fun getLayoutResource() = R.layout.activity_opciones

    private fun llamaAdaptdor() {
        val eListado =
            if (titulo == "Cursos" || titulo == "Kinder" || titulo == "Primaria") EListado.SELECCIONA_OPCION_SIN_PLANTEL else EListado.SELECCIONA_OPCION

        // Llama Adaptador
        rcv_opciones_id.layoutManager = LinearLayoutManager(this)
        rcv_opciones_id.setHasFixedSize(true)
        rcv_opciones_id.swapAdapter(CustomAdapterOpciones(null, gradoList, eListado) {
            if (eListado === EListado.SELECCIONA_OPCION) {
                val bundle = Bundle()
                val intent: Intent
                if (titulo == "Diplomados") {
                    intent = Intent(this, ListaOpcionSeleccionadaDiplomadosActivity::class.java)
                    bundle.putString(BUNDLE_NOMBRE_OPCION, titulo)
                    bundle.putStringArrayList(
                        BUNDLE_OPCION_SELECCIONADA, if (it === 0)
                            resources.getStringArray(R.array.lista_diplomado_psicologia).toList() as ArrayList<String>?
                        else if (it === 1)
                            resources.getStringArray(R.array.lista_diplomado_derecho_criminologia).toList() as ArrayList<String>?
                        else
                            resources.getStringArray(R.array.lista_diplomado_criminalistica).toList() as ArrayList<String>?
                    )
                } else {
                    intent = Intent(this, DescripcionActivity::class.java)
                    bundle.putString(
                        BUNDLE_DESCRIPCION,
                        gradoList?.get(it)?.descripcion
                    )
                    bundle.putString(BUNDLE_NOMBRE_OPCION, titulo)
                }
                intent.putExtras(bundle)
                startActivity(intent)
            }

        }, true)
    }
}