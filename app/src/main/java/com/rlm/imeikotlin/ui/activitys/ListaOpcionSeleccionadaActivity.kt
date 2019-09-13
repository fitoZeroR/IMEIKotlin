package com.rlm.imeikotlin.ui.activitys

import android.content.Intent
import android.os.Bundle
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.local.entity.OpcionEstudioEntity
import com.rlm.imeikotlin.repository.remote.model.response.Grado
import com.rlm.imeikotlin.ui.adapters.CustomAdapterOpciones
import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
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

        intent.extras.let { it ->
            titulo = it.getString(BUNDLE_NOMBRE_OPCION)
            title = titulo

            indice = it.getInt(BUNDLE_INDICE_OPCION)

            opcionSeleccionada = it.getString(BUNDLE_OPCION_SELECCIONADA)
            opcionEstudioEntityList = Moshi.Builder().build().adapter<List<OpcionEstudioEntity>>(Types.newParameterizedType(List::class.java, OpcionEstudioEntity::class.java)).fromJson(opcionSeleccionada)!!
            opcionEstudioEntityList.forEach {
                mutableSetTitulo.add(it.encabezado)
            }
        }

        llamaAdaptdor()
    }

    override fun getLayoutResource() = R.layout.activity_opciones

    private fun llamaAdaptdor() {
        rcv_opciones_id.also { it ->
            if (indice == 9) {
                mutableGradoList =
                    Moshi.Builder().build().adapter<List<Grado>>(Types.newParameterizedType(List::class.java, Grado::class.java)).fromJson(opcionEstudioEntityList[opcionEstudioEntityList.size - 2].listaDiplomados) as MutableList<Grado>
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

            it.adapter = CustomAdapterOpciones(null, mutableGradoList, eListado) { it ->
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
                            BUNDLE_OPCION_SELECCIONADA, when {
                                it === 0 -> (Moshi.Builder().build().adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java)).fromJson(listaOpcionDiplomados[0].listaDiplomados) as ArrayList<String>?)!!
                                it === 1 -> (Moshi.Builder().build().adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java)).fromJson(listaOpcionDiplomados[1].listaDiplomados) as ArrayList<String>?)!!
                                else -> (Moshi.Builder().build().adapter<List<String>>(Types.newParameterizedType(List::class.java, String::class.java)).fromJson(listaOpcionDiplomados[2].listaDiplomados) as ArrayList<String>?)!!
                            }
                        )
                    } else {
                        intent = Intent(this, DescripcionActivity::class.java)
                        bundle.putString(
                            BUNDLE_DESCRIPCION,
                            mutableGradoList[it].descripcion
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