package com.rlm.imeikotlin.repository

import com.rlm.imeikotlin.repository.remote.api.IRetrofitApi
import com.rlm.imeikotlin.utils.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository
@Inject
constructor(private val appExecutors: AppExecutors,
            //private val loginDao: AlumnoDao,
            private val iRetrofitApi: IRetrofitApi) {
}