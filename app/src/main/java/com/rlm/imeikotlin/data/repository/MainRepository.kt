package com.rlm.imeikotlin.data.repository

import com.rlm.imeikotlin.data.local.dao.AlumnoDao
import com.rlm.imeikotlin.data.local.dao.DetalleAlumnoViewDao
import com.rlm.imeikotlin.data.local.dao.InformacionDao
import com.rlm.imeikotlin.data.local.entity.AlumnoEntity
import com.rlm.imeikotlin.data.local.entity.InformacionEntity
import com.rlm.imeikotlin.data.local.entity.embedded.Pago
import com.rlm.imeikotlin.data.local.entity.embedded.Plan
import com.rlm.imeikotlin.data.remote.api.ImeiRemoteDataSource
import com.rlm.imeikotlin.data.remote.model.response.PagosAsignaturasResponse
import com.rlm.imeikotlin.data.repository.strategy.resultLiveData
import com.rlm.imeikotlin.data.repository.strategy.resultLiveDataRest
import com.rlm.imeikotlin.data.repository.strategy.returnLiveDataResponse
import com.rlm.imeikotlin.utils.ContextProviders
import com.rlm.imeikotlin.utils.TIPO_PAGO
import com.rlm.imeikotlin.utils.TIPO_PLAN
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository
@Inject
constructor(
    private val informacionDao: InformacionDao,
    private val detalleAlumnoViewDao: DetalleAlumnoViewDao,
    private val alumnoDao: AlumnoDao,
    private val imeiRemoteDataSource: ImeiRemoteDataSource,
    private val contextProviders: ContextProviders
) {
    lateinit var alumnoEntity: AlumnoEntity

    /*lateinit var alumnoEntity: AlumnoEntity

    fun loadAllInformation(): LiveData<Resource<List<DetalleAlumnoView>>> =
        object : DetailNetworkResource<List<DetalleAlumnoView>, PagosAsignaturasResponse>(appExecutors) {
            override fun saveCallResult(item: PagosAsignaturasResponse) {
                item.data.pagos.forEachIndexed { indexPago, pagos ->
                    item.data.pagos[indexPago].estatusPagos.forEach {
                        informacionDao.saveInformacion(
                            InformacionEntity(
                                alumnoEntity.id, pagos.cuatrimestre,
                                pagos.nombre, TIPO_PAGO, null, Pago(it.pago, it.nombre, it.estatus)
                            )
                        )
                    }
                }

                item.data.plan.forEachIndexed { indexPlan, plan ->
                    item.data.plan[indexPlan].materia.forEach {
                        informacionDao.saveInformacion(
                            InformacionEntity(
                                alumnoEntity.id, plan.idCuatrimestre,
                                plan.nombre, TIPO_PLAN, Plan(it.idMateria, it.materia, it.estatus), null
                            )
                        )
                    }
                }
            }

            override fun shouldFetch(data: List<DetalleAlumnoView>?): Boolean =
                data == null || data.isEmpty()

            override fun loadFromDb(): LiveData<List<DetalleAlumnoView>> =
                detalleAlumnoViewDao.getDetalleAlumno()

            override fun createCall(): LiveData<ApiResponse<PagosAsignaturasResponse>> {
                alumnoEntity = obtieneAlumno()
                return iRetrofitService.obtieneAsignaturasPagos(alumnoEntity.tokenSesion!!)
            }

        }.asLiveData()

    fun downloadFileOnFromServer() =
        object : NetworkResource<DescargaBoletaResponse>() {
            override fun createCall() =
                iRetrofitService.obtieneBoleta(obtieneAlumno().tokenSesion!!)
        }.asLiveData()

    fun changeImageOnFromServer(base64: String) =
        object : NetworkResource<FotoResponse>() {
            override fun createCall(): LiveData<ApiResponse<FotoResponse>> =
                iRetrofitService.enviarFotografia(obtieneAlumno().tokenSesion!!, base64)
        }.asLiveData()

    fun cleanLogin(): Int = doAsyncResult {
        val resultado = alumnoDao.deleteAlumno(alumnoDao.getAlumno())
        resultado
    }.get()

    private fun obtieneAlumno() = doAsyncResult {
        val resultado = alumnoDao.getAlumno()
        resultado
    }.get()*/

    val loadAllInformation = resultLiveData(
        databaseQuery = { detalleAlumnoViewDao.getDetalleAlumno() },
        networkCall = {
            alumnoEntity = alumnoDao.getAlumno()
            imeiRemoteDataSource.fetchDataGetAsignaturasPagos(alumnoEntity.tokenSesion!!)
        },
        saveCallResult = { saveResultPagosAsignaturas(it) })

    private fun saveResultPagosAsignaturas(pagosAsignaturasResponse: PagosAsignaturasResponse) {
        pagosAsignaturasResponse.data.pagos.forEachIndexed { indexPago, pagos ->
            pagosAsignaturasResponse.data.pagos[indexPago].estatusPagos.forEach {
                informacionDao.saveInformacion(
                    InformacionEntity(
                        alumnoEntity.id, pagos.cuatrimestre,
                        pagos.nombre, TIPO_PAGO, null, Pago(it.pago, it.nombre, it.estatus)
                    )
                )
            }
        }

        pagosAsignaturasResponse.data.plan.forEachIndexed { indexPlan, plan ->
            pagosAsignaturasResponse.data.plan[indexPlan].materia.forEach {
                informacionDao.saveInformacion(
                    InformacionEntity(
                        alumnoEntity.id, plan.idCuatrimestre,
                        plan.nombre, TIPO_PLAN, Plan(it.idMateria, it.materia, it.estatus), null
                    )
                )
            }
        }
    }

    val downloadFile = resultLiveDataRest(
        networkCall = { imeiRemoteDataSource.fetchDataGetBoleta(alumnoDao.getAlumno().tokenSesion!!) },
        returnData = { returnLiveDataResponse(it) })

    fun changeImage(base64: String) =
        resultLiveDataRest(
            networkCall = {
                imeiRemoteDataSource.submitDataPhoto(
                    alumnoDao.getAlumno().tokenSesion!!,
                    base64
                )
            },
            returnData = { returnLiveDataResponse(it) })

    fun cleanLogin(): Int {
        var resultado = 0
        GlobalScope.launch(contextProviders.IO) {
            resultado = alumnoDao.deleteAlumno(alumnoDao.getAlumno())
        }
        return resultado
    }
}