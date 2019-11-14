package com.rlm.imeikotlin.data.repository

import androidx.lifecycle.LiveData
import com.rlm.imeikotlin.data.remote.api.ImeiRemoteDataSource
import com.rlm.imeikotlin.data.remote.model.request.EnviarInformacionRequest
import com.rlm.imeikotlin.data.remote.model.response.InformacionPlantelesResponse
import com.rlm.imeikotlin.data.resultLiveDataPost
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

    fun sendInformation(enviarInformacionRequest: EnviarInformacionRequest)
            = resultLiveDataPost(
        networkCall = { imeiRemoteDataSource.fetchDataGetCampus() },
        returnData = { returnLiveDataResponse(enviarInformacionRequest) } )

    private fun returnLiveDataResponse(informacionPlantelesResponse: InformacionPlantelesResponse): LiveData<InformacionPlantelesResponse> {

    }
}