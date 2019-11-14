package com.rlm.imeikotlin.data.repository

import com.rlm.imeikotlin.data.remote.api.ImeiRemoteDataSource
import com.rlm.imeikotlin.data.remote.model.request.EnviarInformacionRequest
import com.rlm.imeikotlin.data.repository.strategy.resultLiveDataRest
import com.rlm.imeikotlin.data.repository.strategy.returnLiveDataResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnviarInformacionRepository
@Inject
constructor(private val imeiRemoteDataSource: ImeiRemoteDataSource) {
    /*fun saveEnviarInformacionOnFromServer(enviarInformacionRequest: EnviarInformacionRequest): LiveData<Resource<EnviarInformacionResponse>> {
        return object : NetworkResource<EnviarInformacionResponse>() {
            override fun createCall() = iRetrofitService.enviarInformacion(enviarInformacionRequest.nombre,
                enviarInformacionRequest.telefono, enviarInformacionRequest.correo, enviarInformacionRequest.interes,
                enviarInformacionRequest.comentarios)
            //iRetrofitService.enviarInformacion(enviarInformacionRequest)
        }.asLiveData()
    }*/

    fun sendInformation(enviarInformacionRequest: EnviarInformacionRequest) =
        resultLiveDataRest(
            networkCall = { imeiRemoteDataSource.submitDataInformation(enviarInformacionRequest) },
            returnData = { returnLiveDataResponse(it) })
}