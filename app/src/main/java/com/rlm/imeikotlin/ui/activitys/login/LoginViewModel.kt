package com.rlm.imeikotlin.ui.activitys.login

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rlm.imeikotlin.repository.LoginRepository
import com.rlm.imeikotlin.repository.remote.modelo.RecuperarPassword
import com.rlm.imeikotlin.utils.AbsentLiveData
import com.rlm.imeikotlin.utils.Resource
import javax.inject.Inject

class LoginViewModel
@Inject
constructor(private val loginRepository: LoginRepository) : ViewModel() {
    @VisibleForTesting
    private val cambiaPasswordMutableLiveData: MutableLiveData<String> = MutableLiveData()
    var postCambiaPasswordResourceLiveData: LiveData<Resource<RecuperarPassword>>

    init {
        postCambiaPasswordResourceLiveData = Transformations.switchMap(cambiaPasswordMutableLiveData) {
            if (it == null) AbsentLiveData.create()
            else loginRepository.saveUserOnFromServer(it)
        }
    }

    fun changePasswordOnFromServer(newPassword: String) {
        cambiaPasswordMutableLiveData.value = newPassword
    }
}