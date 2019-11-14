package com.rlm.imeikotlin.ui.activitys.enviarInformacion

import androidx.lifecycle.*
import com.rlm.imeikotlin.data.repository.EnviarInformacionRepository
import com.rlm.imeikotlin.data.remote.model.request.EnviarInformacionRequest
import javax.inject.Inject

class EnviarInformacionViewModel
@Inject
constructor(private val enviarInformacionRepository: EnviarInformacionRepository) : ViewModel() {
    private val enviarInformacionRequestMutableLiveData: MutableLiveData<EnviarInformacionRequest> = MutableLiveData()
    val postEnviarInformacionResourceLiveData = enviarInformacionRequestMutableLiveData.switchMap { enviarInformacionRepository.sendInformation(it) }

    fun saveEnviarInformacionOnFromServer(enviarInformacionRequest: EnviarInformacionRequest) {
        if (enviarInformacionRequestMutableLiveData.value == enviarInformacionRequest) {
            return
        }
        enviarInformacionRequestMutableLiveData.value = enviarInformacionRequest
    }
}