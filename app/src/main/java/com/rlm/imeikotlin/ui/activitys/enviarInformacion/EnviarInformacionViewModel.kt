package com.rlm.imeikotlin.ui.activitys.enviarInformacion

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.repository.EnviarInformacionRepository
import com.rlm.imeikotlin.repository.remote.modelo.response.EnviarInformacionResponse
import com.rlm.imeikotlin.repository.remote.modelo.request.EnviarInformacionRequest
import com.rlm.imeikotlin.utils.AbsentLiveData
import com.rlm.imeikotlin.utils.Resource
import javax.inject.Inject

class EnviarInformacionViewModel
@Inject
constructor(private val enviarInformacionRepository: EnviarInformacionRepository) : ViewModel() {
    @VisibleForTesting
    private val enviarInformacionRequestMutableLiveData: MutableLiveData<EnviarInformacionRequest> = MutableLiveData()
    var postEnviarInformacionResourceLiveData: LiveData<Resource<EnviarInformacionResponse>>

    init {
        postEnviarInformacionResourceLiveData = Transformations.switchMap(enviarInformacionRequestMutableLiveData) {
            if (it == null) AbsentLiveData.create()
            else enviarInformacionRepository.saveEnviarInformacionOnFromServer(it)
        }
    }

    fun saveEnviarInformacionOnFromServer(enviarInformacionRequest: EnviarInformacionRequest) {
        enviarInformacionRequestMutableLiveData.value = enviarInformacionRequest
    }
}