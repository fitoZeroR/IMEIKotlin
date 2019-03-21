package com.rlm.imeikotlin.repository

import androidx.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.rlm.imeikotlin.repository.api.IRetrofitApi
import com.rlm.imeikotlin.repository.local.dao.OpcionDao
import com.rlm.imeikotlin.repository.local.entity.OpcionEntity
import com.rlm.imeikotlin.repository.modelo.Opciones
import com.rlm.imeikotlin.utils.AppExecutors
import com.rlm.imeikotlin.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpcionesRepository
@Inject
constructor(private val appExecutors: AppExecutors,
            private val opcionDao: OpcionDao,
            private val iRetrofitApi: IRetrofitApi) {

    fun loadAllOptions(): LiveData<Resource<OpcionEntity>> {
        return object : ProcessedNetworkResource<Opciones, OpcionEntity>() {
            override fun createCall(): LiveData<ApiResponse<Opciones>> =
                iRetrofitApi.obtieneOpciones()

            override fun processResponse(opciones: Opciones): OpcionEntity? =
                /*opciones.data.map {
                    OpcionEntity(it.id, it.firstName, it.lastName)
                }*/ OpcionEntity(1)
        }.asLiveData()
    }
}