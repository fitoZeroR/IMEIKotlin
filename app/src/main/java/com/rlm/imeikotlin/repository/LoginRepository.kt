package com.rlm.imeikotlin.repository

import androidx.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.rlm.imeikotlin.repository.api.IRetrofitApi
import com.rlm.imeikotlin.repository.local.dao.LoginDao
import com.rlm.imeikotlin.repository.modelo.RecuperarPassword
import com.rlm.imeikotlin.utils.AppExecutors
import com.rlm.imeikotlin.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository
@Inject
constructor(private val appExecutors: AppExecutors,
            private val loginDao: LoginDao,
            private val iRetrofitApi: IRetrofitApi) {

    fun saveUserOnFromServer(newPassword: String): LiveData<Resource<RecuperarPassword>> {
        return object : NetworkResource<RecuperarPassword>(){
            override fun createCall()
                    = iRetrofitApi.recuperaPassword(newPassword)
        }.asLiveData()
    }
}