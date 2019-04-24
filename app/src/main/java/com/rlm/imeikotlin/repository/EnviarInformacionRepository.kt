package com.rlm.imeikotlin.repository

import androidx.lifecycle.LiveData
import com.rlm.imeikotlin.repository.remote.service.IRetrofitApi
import com.rlm.imeikotlin.repository.remote.model.request.EnviarInformacionRequest
import com.rlm.imeikotlin.repository.remote.model.response.EnviarInformacionResponse
import com.rlm.imeikotlin.utils.Resource
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