package com.rlm.imeikotlin.data.repository

import androidx.lifecycle.LiveData
import com.rlm.imeikotlin.data.DetailNetworkResource
import com.rlm.imeikotlin.data.NetworkResource
import com.rlm.imeikotlin.data.Resource
import com.rlm.imeikotlin.data.remote.api.IRetrofitService
import com.rlm.imeikotlin.data.local.dao.AlumnoDao
import com.rlm.imeikotlin.data.local.entity.AlumnoEntity
import com.rlm.imeikotlin.data.remote.model.response.LoginResponse
import com.rlm.imeikotlin.data.remote.model.response.RecuperarPasswordResponse
import org.jetbrains.anko.doAsyncResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository
@Inject
constructor(
    private val alumnoDao: AlumnoDao,
    private val iRetrofitService: IRetrofitService
) {

    fun saveUserOnFromServer(newPassword: String) =
        object : NetworkResource<RecuperarPasswordResponse>() {
            override fun createCall() = iRetrofitService.recuperaPassword(newPassword)
        }.asLiveData()

    fun getLoginFromServer(usuario: String, password: String): LiveData<Resource<AlumnoEntity>> =
        object : DetailNetworkResource<AlumnoEntity, LoginResponse>(appExecutors) {
            override fun saveCallResult(item: LoginResponse) {
                alumnoDao.clearAlumno()

                alumnoDao.save(with(item.data.alumno) {
                    AlumnoEntity(
                        item.data.tokenSesion, usuario, password, this?.idAlumno, this?.idLicenciatura,
                        this?.idPlantel, this?.nombre, this?.paterno, this?.materno, this?.nacimiento,
                        this?.licenciatura, this?.matricula, this?.curp, this?.foto, this?.cuatrimestre, this?.plantel,
                        this?.telefono, item.message
                    )
                })
            }

            override fun shouldFetch(data: AlumnoEntity?): Boolean =
                !(data?.usuario.equals(usuario) && data?.password.equals(password))

            override fun loadFromDb(): LiveData<AlumnoEntity> =
                alumnoDao.getAlumnoLogin(usuario, password)

            override fun createCall(): LiveData<ApiResponse<LoginResponse>> =
                iRetrofitService.autenticarUsuario(usuario, password)

        }.asLiveData()

    fun validaLogin(): Int = doAsyncResult {
        val validaLogin = alumnoDao.getTotalAlumno()
        //onComplete { valida }
        validaLogin
    }.get()
}