package com.rlm.imeikotlin.ui.activitys.login

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.repository.LoginRepository
import com.rlm.imeikotlin.repository.local.entity.AlumnoEntity
import com.rlm.imeikotlin.repository.remote.model.request.LoginRequest
import com.rlm.imeikotlin.repository.remote.model.response.RecuperarPasswordResponse
import com.rlm.imeikotlin.utils.AbsentLiveData
import com.rlm.imeikotlin.utils.Resource
import javax.inject.Inject

class LoginViewModel
@Inject
constructor(private val loginRepository: LoginRepository) : ViewModel() {
    @VisibleForTesting
    private val cambiaPasswordMutableLiveData: MutableLiveData<String> = MutableLiveData()
    var postCambiaPasswordResponseResourceLiveData: LiveData<Resource<RecuperarPasswordResponse>>

    @VisibleForTesting
    private val loginRequestMutableLiveData: MutableLiveData<LoginRequest> = MutableLiveData()
    var getLoginResourceLiveData: LiveData<Resource<AlumnoEntity>>

    init {
        postCambiaPasswordResponseResourceLiveData = Transformations.switchMap(cambiaPasswordMutableLiveData) {
            if (it == null) AbsentLiveData.create()
            else loginRepository.saveUserOnFromServer(it)
        }

        getLoginResourceLiveData = Transformations.switchMap(loginRequestMutableLiveData) {
            if (it == null) AbsentLiveData.create()
            else loginRepository.getLoginFromServer(it.usuario, it.password)
        }
    }

    fun changePasswordOnFromServer(newPassword: String) {
        cambiaPasswordMutableLiveData.value = newPassword
    }

    fun getLoginFromServer(loginRequest: LoginRequest) {
        loginRequestMutableLiveData.value = loginRequest
    }

    fun verificaLogin(): Int = loginRepository.validaLogin()
}