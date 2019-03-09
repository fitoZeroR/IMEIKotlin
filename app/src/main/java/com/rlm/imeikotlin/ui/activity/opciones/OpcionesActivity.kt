package com.rlm.imeikotlin.ui.activity.opciones

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rlm.imeikotlin.R
import com.rlm.imeikotlin.repository.modelo.*
import com.rlm.imeikotlin.presenter.OpcionesPresenter
import com.rlm.imeikotlin.ui.activity.DescripcionActivity
import com.rlm.imeikotlin.ui.activity.ListaOpcionSeleccionadaActivity
import com.rlm.imeikotlin.ui.activity.plantel.PlantelesActivity
import com.rlm.imeikotlin.ui.activity.BaseActivity
import com.rlm.imeikotlin.ui.adapter.CustomAdapterOpciones
import com.rlm.imeikotlin.utils.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_opciones.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.wifiManager
import java.util.*
import javax.inject.Inject

class OpcionesActivity : BaseActivity() {
    @Inject
    lateinit var opcionesPresenter: OpcionesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        Objects.requireNonNull(supportActionBar)?.setHomeAsUpIndicator(R.drawable.ic_chevron_left)

        opcionesPresenter.setView(this)

        opcionesPresenter.consultaListaOpciones()
    }

    override fun onDestroy() {
        opcionesPresenter.terminate()
        super.onDestroy()
    }

    override fun getLayoutResource() = R.layout.activity_opciones

    override fun despliegaOpciones(opciones: Opciones) {
        val arrayOpciones = resources.getStringArray(R.array.titulo_opciones)

        // Llama Adaptador
        rcv_opciones_id.layoutManager = LinearLayoutManager(this)
        rcv_opciones_id.setHasFixedSize(true)
        rcv_opciones_id.swapAdapter(CustomAdapterOpciones(arrayOpciones.toList(), null, EListado.OPCION) {
            //toast("${it.title} Clicked")
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
                    startActivity<ListaOpcionSeleccionadaActivity>(BUNDLE_OPCION_SELECCIONADA to if (it === 1)
                        Gson().toJson(opciones.kinder)
                    else if (it === 2)
                        Gson().toJson(opciones.primaria)
                    else if (it === 3)
                        Gson().toJson(opciones.bachillerato)
                    else if (it === 4)
                        Gson().toJson(opciones.licenciaturas)
                    else if (it === 5)
                        Gson().toJson(opciones.maestrias)
                    else if (it === 6)
                        Gson().toJson(opciones.doctorados)
                    else if (it === 8)
                        Gson().toJson(opciones.diplomados)
                    else
                        cursos,
                        BUNDLE_NOMBRE_OPCION to arrayOpciones[it])
                }
                7 // Planteles
                -> if (wifiManager.isWifiEnabled())
                    startActivity<PlantelesActivity>(BUNDLE_NOMBRE_OPCION to arrayOpciones[it])
                else
                    Tools.informaErrorConexionWifi(
                        this@OpcionesActivity,
                        getString(R.string.msg_no_conexion_internet),
                        false
                    )
                0, // Que es Grupo Educativo IMEI
                10 // Aviso de privacidad
                -> {
                    startActivity<DescripcionActivity>(BUNDLE_DESCRIPCION to if (it === 0)
                        opciones.somos[0].descripcion
                    else
                        opciones.somos.get(0).descripcionAviso,
                        BUNDLE_NOMBRE_OPCION to  arrayOpciones[it])

                }
            }
        }, true)
    }

    private val cursos = "[\n" +
            "  {\n" +
            "    \"titulo\": \"AMOR A SÍ MISMO Y AMOR A LOS DEMÁS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"AMOR INMADURO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"AMOR Y ODIO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"ANSIEDAD\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"AUTOESTIMA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"BIEN Y MAL\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"CARENCIAS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"CAUSA Y EFECTO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"CAPACITACION INTEGRAL\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"CUANDO UN ACTO VIVIDO FORTALECE O DEBILITA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"COMO COMPARTIR EN GRUPO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"COMO SE VIVE EL AMOR\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"COMO  VIVO MI SEXUALIDAD\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"COMPETENCIA CON OTROS Y SER COMPETENTE CONMIGO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"CONCIENCIA DIVIDIDA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"DERROTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"DEFENSA ANTE EL DOLOR\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"DISCIPLINA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EGOISMO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EL AMOR ASI MISMO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EMOCIONES EN CHOQUE\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EL LÍMITE Y TOTALIDAD DE LO QUE SOY\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EL MIEDO ESTANCA O SUPERA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EL PECADO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EL TRAUMA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EN BUSCA DE LA CONCIENCIA (CONOCIMIENTO)\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EN BUSCA DE LA TOTALIDAD DE UNO MISMO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"EN DONDE SE INVIERTE MI ENERGIA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"GUERRA CON EL DEPREDADOR\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"GUERRAS INTERNAS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"HERRAMIENTAS PARA LOGRAR UN CARACTER DE PROSPERIDAD\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"HUMILDAD\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"INHABILIDAD PARA ENFRENTAR LA CRISIS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"INSEGURIDAD\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"INTELIGENCIA SANA Y NEUROTICA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA BÚSQUEDA DE SIGNIFICADO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA BÚSQUEDA DEL BIEN UNA ACTITUD NEUROTICA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA DESTRUCCION DE MI SER\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA CRÍTICA COMO UNA HERRAMIENTA PARA DESPLOMAR EL OFENDERSE\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LAS DOS MENTES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA LUCHA ENTRE EL ENGAÑO Y EL DARSE CUENTA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA MUJER HOY\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA NECESIDAD DE SER UN HÉROE O DE TENER UNO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA RAZÓN Y LO DESCONOCIDO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA TERQUEDAD DE LA PERCEPCIÓN\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA RESPONSABILIDAD DE MI VIDA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA RUPTURA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LENGUAJE Y ACTO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LIBERTAD\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LA PREOCUPACION DESGASTA MI ENERGIA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LO ABSTRACTO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LOS AMARRES QUE SOSTIENEN LO QUE NO SOMOS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LÍMITE Y TOTALIDAD DE LO QUE SOY \"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"LOCURA O CORDURA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"MIS TRAUMAS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"MAS ALLÁ DE LA SINTAXTIS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"MEDITACION\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"MODALIDADES DE LA PERSONALIDAD\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"MORAL FAMILIAR\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"NECESIDADES MENTALES Y EMOCIONALES (SUFRIMIENTO)\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"NEGACIÓN DE RECUERDOS INFANTILES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"NUESTRA HISTORIA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"RESISTENCIA AL CAMBIO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"OBSESIÓN\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"OBSTÁCULOS CONCEPTUALES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"OBSTÁCULOS EXISTENCIALES QUE IMPIDEN MI REALIZACION\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"ORGULLOS LASTIMADOS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"PENSAMIENTO CRÍTICO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"PERCEPCIÓN\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"PERSONALIDAD\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"PRIMERA ATENCIÓN\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"POCA TOLERANCIA A LA FRUSTRACIÓN\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"PODER Y MISERIA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"PORQUERIA CONCEPTUAL\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"PREJUICIOS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"PREMIO Y CASTIGO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"PRINCIPALES CAUSAS DEL SUFRIMIENTO\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"RELACIÓN PADRES E HIJOS\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"RELACIÓN DE PAREJA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"TRINCHERA CONCEPTUAL\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"SUMISION\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"TOMA DE DECISIONES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"VIRUS MENTAL\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"titulo\": \"VOLUNTAD\"\n" +
            "  }\n" +
            "]"
}