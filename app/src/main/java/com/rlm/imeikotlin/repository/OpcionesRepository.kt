package com.rlm.imeikotlin.repository

import androidx.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.rlm.imeikotlin.repository.remote.service.IRetrofitApi
import com.rlm.imeikotlin.repository.local.entity.OpcionEstudioEntity
import com.rlm.imeikotlin.repository.remote.model.response.OpcionesResponse
import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpcionesRepository
@Inject
constructor(private val iRetrofitApi: IRetrofitApi) {

    fun loadAllOptions(): LiveData<Resource<List<OpcionEstudioEntity>>> {
        return object : ProcessedNetworkResource<OpcionesResponse, List<OpcionEstudioEntity>>() {
            override fun createCall(): LiveData<ApiResponse<OpcionesResponse>> =
                iRetrofitApi.obtieneOpciones()

            override fun processResponse(opcionesResponse: OpcionesResponse): List<OpcionEstudioEntity>? {
                val opcionEstudioEntityList: MutableList<OpcionEstudioEntity> = mutableListOf()

                // 1. Que es Grupo Educativo IMEI
                opcionEstudioEntityList.add(
                    OpcionEstudioEntity(
                        encabezadoOpciones[0],
                        opcionesResponse.somos[0].descripcion!!,
                        opcionesResponse.somos[0].titulo,
                        opcionesResponse.somos[0].planteles,
                        ""
                    )
                )

                // 2. Kinder
                opcionesResponse.kinder.forEach {
                    opcionEstudioEntityList.add(
                        OpcionEstudioEntity(
                            encabezadoOpciones[1],
                            it.descripcion!!,
                            it.titulo,
                            it.planteles,
                            ""
                        )
                    )
                }

                // 3. Primaria
                opcionesResponse.primaria.forEach {
                    opcionEstudioEntityList.add(
                        OpcionEstudioEntity(
                            encabezadoOpciones[2],
                            it.descripcion!!,
                            it.titulo,
                            it.planteles,
                            ""
                        )
                    )
                }

                // 4. Bachillerato Tecnológico
                opcionesResponse.bachillerato.forEach {
                    opcionEstudioEntityList.add(
                        OpcionEstudioEntity(
                            encabezadoOpciones[3],
                            it.descripcion!!,
                            it.titulo,
                            it.planteles,
                            ""
                        )
                    )
                }

                // 5. Licenciaturas
                opcionesResponse.licenciaturas.forEach {
                    opcionEstudioEntityList.add(
                        OpcionEstudioEntity(
                            encabezadoOpciones[4],
                            it.descripcion!!,
                            it.titulo,
                            it.planteles,
                            ""
                        )
                    )
                }

                // 6. Maestrias
                opcionesResponse.maestrias.forEach {
                    opcionEstudioEntityList.add(
                        OpcionEstudioEntity(
                            encabezadoOpciones[5],
                            it.descripcion!!,
                            it.titulo,
                            it.planteles,
                            ""
                        )
                    )
                }

                // 7. Doctorados
                opcionesResponse.doctorados.forEach {
                    opcionEstudioEntityList.add(
                        OpcionEstudioEntity(
                            encabezadoOpciones[6],
                            it.descripcion!!,
                            it.titulo,
                            it.planteles,
                            ""
                        )
                    )
                }

                // 8. Planteles
                opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[7], "", "", "", ""))

                // 9. Diplomados
                for (x in opcionesResponse.diplomados.indices) {
                    opcionEstudioEntityList.add(
                        OpcionEstudioEntity(
                            encabezadoOpciones[8],
                            opcionesResponse.diplomados[x].descripcion!!,
                            opcionesResponse.diplomados[x].titulo,
                            opcionesResponse.diplomados[x].planteles,
                            when (x) {
                                /*1 -> Gson().toJson(listaDiplomadoPsicologia)
                                2 -> Gson().toJson(listaDiplomadoDerechoCriminologia)
                                else -> Gson().toJson(listaDiplomadoCriminalistica)*/
                                1 -> Moshi.Builder().build().adapter<Array<String>>(Array<String>::class.java).toJson(listaDiplomadoPsicologia)
                                2 -> Moshi.Builder().build().adapter<Array<String>>(Array<String> ::class.java).toJson(listaDiplomadoDerechoCriminologia)
                                else -> Moshi.Builder().build().adapter<Array<String>>(Array<String> ::class.java).toJson(listaDiplomadoCriminalistica)
                            }
                        )
                    )
                }

                // 10. Cursos
                opcionEstudioEntityList.add(OpcionEstudioEntity(encabezadoOpciones[9], "", "", "", cursos))

                // 11. Aviso de privacidad
                opcionEstudioEntityList.add(
                    OpcionEstudioEntity(
                        encabezadoOpciones[10],
                        opcionesResponse.somos[0].descripcionAviso!!,
                        opcionesResponse.somos[0].titulo,
                        opcionesResponse.somos[0].planteles,
                        ""
                    )
                )

                return opcionEstudioEntityList
            }
        }.asLiveData()
    }
}