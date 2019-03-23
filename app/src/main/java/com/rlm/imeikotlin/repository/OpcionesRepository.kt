package com.rlm.imeikotlin.repository

import androidx.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.google.gson.Gson
import com.rlm.imeikotlin.repository.remote.api.IRetrofitApi
import com.rlm.imeikotlin.repository.local.dao.OpcionEstudioDao
import com.rlm.imeikotlin.repository.local.entity.OpcionEstudioEntity
import com.rlm.imeikotlin.repository.remote.modelo.Opciones
import com.rlm.imeikotlin.utils.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpcionesRepository
@Inject
constructor(private val appExecutors: AppExecutors,
            private val opcionEstudioDao: OpcionEstudioDao,
            private val iRetrofitApi: IRetrofitApi) {

    fun loadAllOptions(): LiveData<Resource<List<OpcionEstudioEntity>>> {
        return object : ProcessedNetworkResource<Opciones, List<OpcionEstudioEntity>>() {
            override fun createCall(): LiveData<ApiResponse<Opciones>> =
                iRetrofitApi.obtieneOpciones()

            override fun processResponse(opciones: Opciones): List<OpcionEstudioEntity>? {
                val opcionEstudioEntityList: MutableList<OpcionEstudioEntity> = mutableListOf()

                // 1. Que es Grupo Educativo IMEI
                opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[0], opciones.somos[0].descripcion, opciones.somos[0].titulo, opciones.somos[0].planteles, ""))

                // 2. Kinder
                opciones.kinder.forEach {
                    opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[1], it.descripcion, it.titulo, it.planteles, ""))
                }

                // 3. Primaria
                opciones.primaria.forEach {
                    opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[2], it.descripcion, it.titulo, it.planteles, ""))
                }

                // 4. Bachillerato Tecnológico
                opciones.bachillerato.forEach {
                    opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[3], it.descripcion, it.titulo, it.planteles, ""))
                }

                // 5. Licenciaturas
                opciones.licenciaturas.forEach {
                    opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[4], it.descripcion, it.titulo, it.planteles, ""))
                }

                // 6. Maestrias
                opciones.maestrias.forEach {
                    opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[5], it.descripcion, it.titulo, it.planteles, ""))
                }

                // 7. Doctorados
                opciones.doctorados.forEach {
                    opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[6], it.descripcion, it.titulo, it.planteles, ""))
                }

                // 8. Planteles
                opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[7], "", "", "",""))

                // 9. Diplomados
                for (x in opciones.diplomados.indices) {
                    opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[8], opciones.diplomados[x].descripcion, opciones.diplomados[x].titulo,
                        opciones.diplomados[x].planteles, when (x) {
                            1 -> Gson().toJson(listaDiplomadoPsicologia)
                            2 -> Gson().toJson(listaDiplomadoDerechoCriminologia)
                            else -> Gson().toJson(listaDiplomadoCriminalistica)
                        }))
                }

                // 10. Cursos
                opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[9], "", "", "", cursos))

                // 11. Aviso de privacidad
                opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[10], opciones.somos[0].descripcionAviso, opciones.somos[0].titulo, opciones.somos[0].planteles, ""))

                return opcionEstudioEntityList
            }
        }.asLiveData()
    }
}