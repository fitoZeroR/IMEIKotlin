package com.rlm.imeikotlin.data.remote.api

import com.rlm.imeikotlin.data.remote.model.request.EnviarInformacionRequest
import javax.inject.Inject

class ImeiRemoteDataSource @Inject constructor(private val iRetrofitService: IRetrofitService) : BaseDataSource() {

    suspend fun fetchDataGetCampus() = getResult { iRetrofitService.obtienePlanteles() }

    suspend fun submitDataInformation(enviarInformacionRequest: EnviarInformacionRequest) =
        getResult {
            iRetrofitService.enviarInformacion(enviarInformacionRequest.nombre,
                enviarInformacionRequest.telefono, enviarInformacionRequest.correo, enviarInformacionRequest.interes,
                enviarInformacionRequest.comentarios)
        }
}