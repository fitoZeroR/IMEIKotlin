package com.rlm.imeikotlin.ui.activitys

import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.local.entity.OpcionEstudioEntity
import com.rlm.imeikotlin.repository.remote.modelo.response.Grado
import com.rlm.imeikotlin.ui.adapters.CustomAdapterOpciones
import com.rlm.imeikotlin.utils.*
import kotlinx.android.synthetic.main.activity_opciones.*
import java.util.*

class ListaOpcionSeleccionadaActivity : BaseActivity() {
    private lateinit var opcionEstudioEntityList: List<OpcionEstudioEntity>
    private lateinit var titulo: String
    private lateinit var opcionSeleccionada: String
    private var indice: Int = 0
    private val mutableSetTitulo: MutableSet<String> = mutableSetOf()
    private lateinit var mutableGradoList: MutableList<Grado>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        intent.extras.let {
            titulo = it.getString(BUNDLE_NOMBRE_OPCION)
            setTitle(titulo)

            indice = it.getInt(BUNDLE_INDICE_OPCION)

            opcionSeleccionada = it.getString(BUNDLE_OPCION_SELECCIONADA)
            opcionEstudioEntityList = Gson().fromJson<List<OpcionEstudioEntity>>(opcionSeleccionada,
                object : TypeToken<ArrayList<OpcionEstudioEntity>>() {}.type
            )
            opcionEstudioEntityList.forEach {
                mutableSetTitulo.add(it.encabezado)
            }
        }

        llamaAdaptdor()
    }

    override fun getLayoutResource() = R.layout.activity_opciones

    private fun llamaAdaptdor() {
        rcv_opciones_id.also {
            if (indice == 9) {
                mutableGradoList = Gson().fromJson<List<Grado>>(opcionEstudioEntityList[opcionEstudioEntityList.size - 2].listaDiplomados,
                    object : TypeToken<ArrayList<Grado>>() {}.type) as MutableList<Grado>
            } else {
                mutableGradoList = mutableListOf()
                opcionEstudioEntityList.filter {
                    it.encabezado.equals(mutableSetTitulo.elementAt(indice))
                }.forEach {
                    mutableGradoList.add(
                        Grado(
                            it.titulo,
                            it.descripcion,
                            "",
                            it.planteles
                        )
                    )
                }
            }

            val eListado =
                if (titulo == "Cursos") EListado.SELECCIONA_OPCION_SIN_PLANTEL else EListado.SELECCIONA_OPCION

            it.adapter = CustomAdapterOpciones(null, mutableGradoList, eListado) {
                if (eListado === EListado.SELECCIONA_OPCION) {
                    val bundle = Bundle()
                    val intent: Intent
                    if (titulo == "Diplomados") {
                        val listaOpcionDiplomados = opcionEstudioEntityList.filter {
                            it.encabezado == mutableSetTitulo.elementAt(8)
                        }
                        intent = Intent(this, ListaOpcionSeleccionadaDiplomadosActivity::class.java)
                        bundle.putString(BUNDLE_NOMBRE_OPCION, titulo)
                        bundle.putStringArrayList(
                            BUNDLE_OPCION_SELECCIONADA, if (it === 0)
                                Gson().fromJson<List<String>>(listaOpcionDiplomados[0].listaDiplomados,
                                    object : TypeToken<ArrayList<String>>() {}.type).toList() as ArrayList<String>?
                            else if (it === 1)
                                Gson().fromJson<List<String>>(listaOpcionDiplomados[1].listaDiplomados,
                                    object : TypeToken<ArrayList<String>>() {}.type).toList() as ArrayList<String>?
                            else
                                Gson().fromJson<List<String>>(listaOpcionDiplomados[2].listaDiplomados,
                                    object : TypeToken<ArrayList<String>>() {}.type).toList() as ArrayList<String>?
                        )
                    } else {
                        intent = Intent(this, DescripcionActivity::class.java)
                        bundle.putString(
                            BUNDLE_DESCRIPCION,
                            mutableGradoList.get(it).descripcion
                        )
                        bundle.putString(BUNDLE_NOMBRE_OPCION, titulo)
                    }
                    intent.putExtras(bundle)
                    startActivity(intent)
                }

            }
        }
    }
}