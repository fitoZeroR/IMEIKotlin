package com.rlm.imeikotlin.repository

import androidx.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.rlm.imeikotlin.repository.remote.api.IRetrofitApi
import com.rlm.imeikotlin.repository.local.dao.AlumnoDao
import com.rlm.imeikotlin.repository.local.entity.AlumnoEntity
import com.rlm.imeikotlin.repository.remote.modelo.response.LoginResponse
import com.rlm.imeikotlin.repository.remote.modelo.response.RecuperarPasswordResponse
import com.rlm.imeikotlin.utils.AppExecutors
import com.rlm.imeikotlin.utils.Resource
import org.jetbrains.anko.doAsyncResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository
@Inject
constructor(private val appExecutors: AppExecutors,
            private val alumnoDao: AlumnoDao,
            private val iRetrofitApi: IRetrofitApi) {

    fun saveUserOnFromServer(newPassword: String): LiveData<Resource<RecuperarPasswordResponse>> {
        return object : NetworkResource<RecuperarPasswordResponse>(){
            override fun createCall()
                    = iRetrofitApi.recuperaPassword(newPassword)
        }.asLiveData()
    }

    fun getLoginFromServer(usuario: String, password: String): LiveData<Resource<AlumnoEntity>> =
        object : DetailNetworkResource<AlumnoEntity, LoginResponse>(appExecutors) {
            override fun saveCallResult(item: LoginResponse) {
                alumnoDao.clearAlumno()

                alumnoDao.save(with(item.data.alumno){
                    AlumnoEntity(item.data.tokenSesion, usuario, password, this?.idAlumno, this?.idLicenciatura,
                        this?.idPlantel, this?.nombre, this?.paterno, this?.materno, this?.nacimiento,
                        this?.licenciatura, this?.matricula, this?.curp, this?.foto, this?.cuatrimestre, this?.plantel,
                        this?.telefono, item.message)
                })
            }

            override fun shouldFetch(data: AlumnoEntity?): Boolean =
                !(data?.usuario.equals(usuario) && data?.password.equals(password))

            override fun loadFromDb(): LiveData<AlumnoEntity> =
                alumnoDao.getAlumnoLogin(usuario, password)

            override fun createCall(): LiveData<ApiResponse<LoginResponse>> =
                iRetrofitApi.autenticarUsuario(usuario, password)

        }.asLiveData()

    fun validaLogin() = doAsyncResult {
            val validaLogin = alumnoDao.getTotalAlumno()
            //onComplete { valida }
            validaLogin
        }.get()
}