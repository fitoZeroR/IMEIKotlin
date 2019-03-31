package com.rlm.imeikotlin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.rlm.imeikotlin.repository.local.dao.AlumnoDao
import com.rlm.imeikotlin.repository.local.dao.DetalleAlumnoViewDao
import com.rlm.imeikotlin.repository.local.dao.InformacionDao
import com.rlm.imeikotlin.repository.local.entity.AlumnoEntity
import com.rlm.imeikotlin.repository.local.entity.InformacionEntity
import com.rlm.imeikotlin.repository.local.entity.embedded.Pago
import com.rlm.imeikotlin.repository.local.entity.embedded.Plan
import com.rlm.imeikotlin.repository.local.view.DetalleAlumnoView
import com.rlm.imeikotlin.repository.remote.api.IRetrofitApi
import com.rlm.imeikotlin.repository.remote.modelo.response.DescargaBoletaResponse
import com.rlm.imeikotlin.repository.remote.modelo.response.PagosAsignaturasResponse
import com.rlm.imeikotlin.utils.AppExecutors
import com.rlm.imeikotlin.utils.Resource
import com.rlm.imeikotlin.utils.TIPO_PAGO
import com.rlm.imeikotlin.utils.TIPO_PLAN
import org.jetbrains.anko.doAsyncResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository
@Inject
constructor(private val appExecutors: AppExecutors,
            private val informacionDao: InformacionDao,
            private val detalleAlumnoViewDao: DetalleAlumnoViewDao,
            private val alumnoDao: AlumnoDao,
            private val iRetrofitApi: IRetrofitApi) {

    lateinit var alumnoEntity: AlumnoEntity

    fun loadAllInformation(): LiveData<Resource<List<DetalleAlumnoView>>> =
        object : DetailNetworkResource<List<DetalleAlumnoView>, PagosAsignaturasResponse>(appExecutors) {
            override fun saveCallResult(item: PagosAsignaturasResponse) {
                item.data.pagos.forEachIndexed { indexPago, pagos ->
                    item.data.pagos[indexPago].estatusPagos.forEach {
                        informacionDao.saveInformacion(InformacionEntity(alumnoEntity.id, pagos.cuatrimestre,
                            pagos.nombre, TIPO_PAGO, null, Pago(it.pago, it.nombre, it.estatus)))
                    }
                }

                item.data.plan.forEachIndexed { indexPlan, plan ->
                    item.data.plan[indexPlan].materia.forEach {
                        informacionDao.saveInformacion(InformacionEntity(alumnoEntity.id, plan.idCuatrimestre,
                            plan.nombre, TIPO_PLAN, Plan(it.idMateria, it.materia, it.estatus), null))
                    }
                }
            }

            override fun shouldFetch(data: List<DetalleAlumnoView>?): Boolean =
                data == null || data.size == 0

            override fun loadFromDb(): LiveData<List<DetalleAlumnoView>> =
                detalleAlumnoViewDao.getDetalleAlumno()

            override fun createCall(): LiveData<ApiResponse<PagosAsignaturasResponse>> {
                alumnoEntity = obtieneAlumno()

                return iRetrofitApi.obtieneAsignaturasPagos(alumnoEntity.tokenSesion!!)
            }

        }.asLiveData()

    fun downloadFileOnFromServer() =
        object : NetworkResource<DescargaBoletaResponse>() {
            override fun createCall() =
                iRetrofitApi.obtieneBoleta(obtieneAlumno().tokenSesion!!)
        }.asLiveData()

    fun cleanLogin() = doAsyncResult {
        //Log.i("RLM", "Tamaño Alumno = " + alumnoDao.getTotalAlumno())
        alumnoDao.clearAlumno()
        //Log.i("RLM", "Tamaño Alumno = " + alumnoDao.getTotalAlumno())
        Unit
    }.get()

    private fun obtieneAlumno() = doAsyncResult {
        val resultado = alumnoDao.getAlumno()
        resultado
    }.get()
}