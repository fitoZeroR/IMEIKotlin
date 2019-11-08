package com.rlm.imeikotlin.data

import androidx.lifecycle.LiveData
import com.rlm.imeikotlin.data.remote.service.IRetrofitApi
import com.rlm.imeikotlin.data.remote.model.request.EnviarInformacionRequest
import com.rlm.imeikotlin.data.remote.model.response.EnviarInformacionResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnviarInformacionRepository
@Inject
constructor(private val iRetrofitApi: IRetrofitApi) {
    fun saveEnviarInformacionOnFromServer(enviarInformacionRequest: EnviarInformacionRequest): LiveData<Resource<EnviarInformacionResponse>> {
        return object : NetworkResource<EnviarInformacionResponse>() {
            override fun createCall() = iRetrofitApi.enviarInformacion(enviarInformacionRequest.nombre,
                enviarInformacionRequest.telefono, enviarInformacionRequest.correo, enviarInformacionRequest.interes,
                enviarInformacionRequest.comentarios)
            //iRetrofitApi.enviarInformacion(enviarInformacionRequest)
        }.asLiveData()
    }
}