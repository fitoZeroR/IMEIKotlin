package com.rlm.imeikotlin.ui.activitys.login

import androidx.lifecycle.*
import com.rlm.imeikotlin.data.repository.LoginRepository
import com.rlm.imeikotlin.data.remote.model.request.LoginRequest
import javax.inject.Inject

class LoginViewModel
@Inject
constructor(private val loginRepository: LoginRepository) : ViewModel() {
    fun verificaLogin(): Int = loginRepository.validaLogin()

    private val cambiaPasswordMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val postCambiaPasswordResponseResourceLiveData = cambiaPasswordMutableLiveData.switchMap { loginRepository.getPassword(it) }

    fun changePasswordOnFromServer(newPassword: String) = apply {
        cambiaPasswordMutableLiveData.value = newPassword
    }

    private val loginRequestMutableLiveData: MutableLiveData<LoginRequest> = MutableLiveData()
    val getLoginResourceLiveData = loginRequestMutableLiveData.switchMap { loginRepository.getLogin(it.usuario, it.password) }

    fun getLoginFromServer(loginRequest: LoginRequest) {
        loginRequestMutableLiveData.value = loginRequest
    }
}