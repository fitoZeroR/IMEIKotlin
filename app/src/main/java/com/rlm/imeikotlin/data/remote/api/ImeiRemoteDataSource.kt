package com.rlm.imeikotlin.data.remote.api

import com.rlm.imeikotlin.data.remote.model.request.EnviarInformacionRequest
import javax.inject.Inject

class ImeiRemoteDataSource @Inject constructor(private val iRetrofitService: IRetrofitService) : BaseDataSource() {

    suspend fun fetchDataGetCampus() = getResult { iRetrofitService.obtienePlanteles() }

    suspend fun fetchDataGetOptions() = getResult { iRetrofitService.obtieneOpciones() }

    suspend fun fetchDataGetPassword(newPassword: String) = getResult { iRetrofitService.recuperaPassword(newPassword) }

    suspend fun fetchDataLogin(usuario: String, password: String) = getResult { iRetrofitService.autenticarUsuario(usuario, password) }

    suspend fun fetchDataGetAsignaturasPagos(tokenSesion: String) = getResult { iRetrofitService.obtieneAsignaturasPagos(tokenSesion) }

    suspend fun fetchDataGetBoleta(tokenSesion: String) = getResult { iRetrofitService.obtieneBoleta(tokenSesion) }

    suspend fun submitDataPhoto(tokenSesion: String, base64: String) = getResult { iRetrofitService.enviarFotografia(tokenSesion, base64) }

    suspend fun submitDataInformation(enviarInformacionRequest: EnviarInformacionRequest) =
        getResult {
            iRetrofitService.enviarInformacion(enviarInformacionRequest.nombre,
                enviarInformacionRequest.telefono, enviarInformacionRequest.correo, enviarInformacionRequest.interes,
                enviarInformacionRequest.comentarios)
        }
}