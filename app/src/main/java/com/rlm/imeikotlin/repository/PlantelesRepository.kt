package com.rlm.imeikotlin.repository

import androidx.lifecycle.LiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.rlm.imeikotlin.repository.local.dao.PlantelDao
import com.rlm.imeikotlin.repository.local.entity.PlantelEntity
import com.rlm.imeikotlin.repository.remote.service.IRetrofitApi
import com.rlm.imeikotlin.repository.remote.model.response.InformacionPlantelesResponse
import com.rlm.imeikotlin.utils.AppExecutors
import com.rlm.imeikotlin.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlantelesRepository
@Inject
constructor(
    private val appExecutors: AppExecutors,
    private val plantelDao: PlantelDao,
    private val iRetrofitApi: IRetrofitApi
) {

    fun loadAllPlanteles(): LiveData<Resource<List<PlantelEntity>>> =
        object : DetailNetworkResource<List<PlantelEntity>, InformacionPlantelesResponse>(appExecutors) {
            override fun saveCallResult(item: InformacionPlantelesResponse) =
                item.planteles.forEach {
                    plantelDao.savePlantel(PlantelEntity(it.nombre, it.latitud, it.longitud))
                }

            override fun shouldFetch(data: List<PlantelEntity>?): Boolean =
                data == null || data.size == 0

            override fun loadFromDb(): LiveData<List<PlantelEntity>> =
                plantelDao.getAllPlanteles()

            override fun createCall(): LiveData<ApiResponse<InformacionPlantelesResponse>> =
                iRetrofitApi.obtienePlanteles()

        }.asLiveData()
}