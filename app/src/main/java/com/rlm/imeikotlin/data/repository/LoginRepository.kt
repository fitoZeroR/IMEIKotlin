package com.rlm.imeikotlin.data.repository

import com.rlm.imeikotlin.data.local.dao.AlumnoDao
import com.rlm.imeikotlin.data.local.entity.AlumnoEntity
import com.rlm.imeikotlin.data.remote.api.ImeiRemoteDataSource
import com.rlm.imeikotlin.data.repository.strategy.resultLiveData
import com.rlm.imeikotlin.data.repository.strategy.resultLiveDataRest
import com.rlm.imeikotlin.data.repository.strategy.returnLiveDataResponse
import com.rlm.imeikotlin.utils.ContextProviders
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository
@Inject
constructor(
    private val alumnoDao: AlumnoDao,
    private val imeiRemoteDataSource: ImeiRemoteDataSource,
    private val contextProviders: ContextProviders
) {
    /*fun saveUserOnFromServer(newPassword: String) =
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
    }.get()*/

    fun validaLogin(): Int {
        var totalAlumno = 0
        GlobalScope.launch(contextProviders.IO) {
            totalAlumno = alumnoDao.getTotalAlumno()
        }
        return totalAlumno
    }

    fun getPassword(newPassword: String) =
        resultLiveDataRest(
            networkCall = { imeiRemoteDataSource.fetchDataGetPassword(newPassword) },
            returnData = { returnLiveDataResponse(it) })

    fun getLogin(usuario: String, password: String) =
        resultLiveData(
            databaseQuery = { alumnoDao.getAlumnoLogin(usuario, password) },
            networkCall = { imeiRemoteDataSource.fetchDataLogin(usuario, password) },
            saveCallResult = {
                alumnoDao.clearAlumno()
                alumnoDao.save(with(it.data.alumno) {
                    AlumnoEntity(
                        it.data.tokenSesion,
                        usuario,
                        password,
                        this?.idAlumno,
                        this?.idLicenciatura,
                        this?.idPlantel,
                        this?.nombre,
                        this?.paterno,
                        this?.materno,
                        this?.nacimiento,
                        this?.licenciatura,
                        this?.matricula,
                        this?.curp,
                        this?.foto,
                        this?.cuatrimestre,
                        this?.plantel,
                        this?.telefono,
                        it.message
                    )
                })
            })
}