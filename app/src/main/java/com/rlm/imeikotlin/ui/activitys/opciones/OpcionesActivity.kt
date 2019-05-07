package com.rlm.imeikotlin.ui.activitys.opciones

import android.os.Bundle
import androidx.lifecycle.Observer
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.local.entity.OpcionEstudioEntity
import com.rlm.imeikotlin.ui.activitys.BaseActivity
import com.rlm.imeikotlin.ui.activitys.DescripcionActivity
import com.rlm.imeikotlin.ui.activitys.ListaOpcionSeleccionadaActivity
import com.rlm.imeikotlin.ui.activitys.planteles.PlantelesActivity
import com.rlm.imeikotlin.ui.adapters.CustomAdapterOpciones
import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_opciones.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.wifiManager
import java.util.*
import javax.inject.Inject

class OpcionesActivity : BaseActivity() {
    @Inject
    lateinit var opcionesViewModel: OpcionesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        subscribeToOpcionModel()

        opcionesViewModel.loadAllOptions(true)
    }

    override fun getLayoutResource() = R.layout.activity_opciones

    private fun subscribeToOpcionModel() {
        opcionesViewModel.getAllOptionsResourceLiveData.observe(this, Observer {
            administraObserverResources(it) {
                despliegaAdaptadorOpciones(it.data!!)
            }
        })
    }

    private fun despliegaAdaptadorOpciones(listaOpcionEstudioEntity: List<OpcionEstudioEntity>) {
        //Setting up Adapter
        //Setting up RecyclerView
        rcv_opciones_id.also {
            val mutableSetTitulo: MutableSet<String> = mutableSetOf()
            listaOpcionEstudioEntity.forEach {
                mutableSetTitulo.add(it.encabezado)
            }

            it.adapter = CustomAdapterOpciones(mutableSetTitulo.toList(), null, EListado.OPCION) {
                when (it) {
                    1, // Kinder
                    2, // Primaria
                    3, // Bachillerato Tecnológico
                    4, // Licenciaturas
                    5, // Maestrias
                    6, // Doctorados
                    8, // Diplomados
                    9 // Cursos
                    -> {
                        startActivity<ListaOpcionSeleccionadaActivity>(
                            BUNDLE_OPCION_SELECCIONADA to
                                    //Gson().toJson(listaOpcionEstudioEntity)
                                    Moshi.Builder().build().adapter<List<OpcionEstudioEntity>>(Types.newParameterizedType(List::class.java, OpcionEstudioEntity::class.java)).toJson(listaOpcionEstudioEntity),
                                    //Moshi.Builder().build().adapter<List<OpcionEstudioEntity>>(List<OpcionEstudioEntity>::class.java).toJson(listaOpcionEstudioEntity),
                            BUNDLE_NOMBRE_OPCION to mutableSetTitulo.elementAt(it),
                            BUNDLE_INDICE_OPCION to it
                        )
                    }
                    7 // Planteles
                    -> if (wifiManager.isWifiEnabled())
                        startActivity<PlantelesActivity>(BUNDLE_NOMBRE_OPCION to mutableSetTitulo.elementAt(it))
                    else
                        Tools.informaErrorConexionWifi(
                            this@OpcionesActivity,
                            getString(R.string.msg_no_conexion_internet),
                            false
                        )
                    0, // Que es Grupo Educativo IMEI
                    10 // Aviso de privacidad
                    -> {
                        startActivity<DescripcionActivity>(
                            BUNDLE_DESCRIPCION to if (it === 0)
                                listaOpcionEstudioEntity[0].descripcion
                            else
                                listaOpcionEstudioEntity[listaOpcionEstudioEntity.size - 1].descripcion,
                            BUNDLE_NOMBRE_OPCION to mutableSetTitulo.elementAt(it)
                        )
                    }
                }
            }
        }
    }
}